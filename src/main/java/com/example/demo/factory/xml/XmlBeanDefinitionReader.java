package com.example.demo.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.example.demo.core.io.Resource;
import com.example.demo.core.io.ResourceLoader;
import com.example.demo.exception.BeansException;
import com.example.demo.factory.config.BeanDefinition;
import com.example.demo.factory.config.BeanDefinitionRegistry;
import com.example.demo.factory.config.BeanReference;
import com.example.demo.factory.support.AbstractBeanDefinitionReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;


public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public static final String BEAN_ELEMENT = "bean";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String INIT_METHOD_ATTRIBUTE = "init-method";
    public static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";


    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try (InputStream inputStream = resource.getInputStream()) {
            doLoadBeanDefinitions(inputStream);
        } catch (IOException ex) {
            throw new BeansException("IOException parsing XML document from " + resource, ex);
        }
    }

    private void doLoadBeanDefinitions(InputStream inputStream) {
        Document document = XmlUtil.readXML(inputStream);
        Element root = document.getDocumentElement();
        NodeList childrenNodes = root.getChildNodes();
        for (int i = 0; i < childrenNodes.getLength(); i++) {
            if (childrenNodes.item(i) instanceof Element) {
                if (BEAN_ELEMENT.equals(childrenNodes.item(i).getNodeName())) {
                    //解析bean标签
                    Element bean = (Element) childrenNodes.item(i);
                    String beanId = bean.getAttribute(ID_ATTRIBUTE);
                    String name = bean.getAttribute(NAME_ATTRIBUTE);
                    String className = bean.getAttribute(CLASS_ATTRIBUTE);
                    String initMethodName = bean.getAttribute(INIT_METHOD_ATTRIBUTE);
                    String destroyMethodName = bean.getAttribute(DESTROY_METHOD_ATTRIBUTE);


                    Class<?> clazz;
                    try {
                        clazz = Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        throw new BeansException("Cannot find class [" + className + "]");
                    }

                    //id优先于name
                    String beanName = StrUtil.isNotBlank(beanId) ? beanId : name;
                    if (StrUtil.isBlank(beanName)) {
                        //如果id和name都为空，将类名的第一个字母转为小写后作为bean的名称
                        beanName = StrUtil.lowerFirst(clazz.getSimpleName());
                    }

                    BeanDefinition beanDefinition = new BeanDefinition(clazz);
                    beanDefinition.setInitMethodName(initMethodName);
                    beanDefinition.setDestoryMethodName(destroyMethodName);

                    for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                        if (PROPERTY_ELEMENT.equals(bean.getChildNodes().item(j).getNodeName())) {
                            //解析property标签
                            Element property = (Element) bean.getChildNodes().item(j);
                            String nameAttribute = property.getAttribute(NAME_ATTRIBUTE);
                            String valueAttribute = property.getAttribute(VALUE_ATTRIBUTE);
                            String refAttribute = property.getAttribute(REF_ATTRIBUTE);

                            if (StrUtil.isBlank(nameAttribute)) {
                                throw new BeansException("The name attribute cannot be null or empty");
                            }

                            Object attributeValue = valueAttribute;
                            if (StrUtil.isNotBlank(refAttribute)) {
                                attributeValue = new BeanReference(refAttribute);
                            }
                            beanDefinition.getPropertyValues().addPropertyValue(nameAttribute, attributeValue);
                        }
                    }

                    if (getRegistry().containsBeanDefinition(beanName)){
                        //beanName 不能重名
                        throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
                    }
                    // 注册BeanDefinition
                    getRegistry().registerBeanDefinition(beanName,beanDefinition);
                }
            }
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);

        //TODO 这里加载环境配置文件
        //TODO 主要是为了学习事件发布
    }
}
