package com.bsoft.libnavcompiler;


import com.bsoft.libnavannotation.WXPayEntry;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by hcDarren on 2017/9/10.
 */
@AutoService(Processor.class)
public class WxProcessor extends AbstractProcessor{
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
    }

    /**
     * 用来指定支持的 AnnotationTypes
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    /**
     * 参考了 ButterKnife 的写法
     *
     * @return
     */
    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(WXPayEntry.class);
        return annotations;
    }

    /**
     * 指定版本
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.print("===================================================");

        System.out.print("===================================================");
        // 生成 一个 Class xxx.wxapi.WXPayEntryActivity extends BaseWXPayActivity
        generateWXPayCode(roundEnvironment);
        return false;
    }

    private void generateWXPayCode(RoundEnvironment roundEnvironment) {
        WXPayEntryVisitor visitor = new WXPayEntryVisitor();
        visitor.setFiler(mFiler);
        scanElement(roundEnvironment,WXPayEntry.class,visitor);
    }

    private void scanElement(RoundEnvironment roundEnvironment, Class<? extends Annotation> annotation, AnnotationValueVisitor visitor){
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(annotation);
        for (Element element : elements) {
            List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();

            for (AnnotationMirror annotationMirror : annotationMirrors) {
                Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();

                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry:elementValues.entrySet()){
                    entry.getValue().accept(visitor,null);
                }
            }
        }
    }
}
