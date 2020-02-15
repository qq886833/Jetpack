package com.bsoft.libnavcompiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;
import java.io.IOException;

/**
 * Created by hcDarren on 2017/9/10.
 */

public class WXPayEntryVisitor extends SimpleAnnotationValueVisitor7<Void,Void>{
    private String mPackageName;
    private TypeMirror mTypeMirror;
    private Filer mFiler;

    @Override
    public Void visitString(String s, Void aVoid) {
        mPackageName = s;
        return aVoid;
    }

    @Override
    public Void visitType(TypeMirror typeMirror, Void aVoid) {
        mTypeMirror = typeMirror;
        generateWXPayCode();
        return aVoid;
    }

    public void setFiler(Filer mFiler) {
        this.mFiler = mFiler;
    }

    /**
     * 生成砸门的类
     */
    private void generateWXPayCode() {
        // 生成 一个 Class xxx.wxapi.WXPayEntryActivity extends BaseWXPayActivity
        TypeSpec.Builder classSpecBuilder = TypeSpec.classBuilder("WXPayEntryActivity")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(TypeName.get(mTypeMirror));

        try {
            JavaFile.builder(mPackageName+".wxapi",classSpecBuilder.build())
                    .addFileComment("微信支付自动生成")
                    .build().writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("翻车了");
        }
    }
}
