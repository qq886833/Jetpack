package com.bsoft.libcommon.model;

import android.text.TextUtils;
import com.bsoft.libcommon.utils.DateUtil;
import com.bsoft.libcommon.utils.StringUtil;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户信息
 * Created by Administrator on 2016/12/7.
 */
public class UserInfoVo implements Serializable {

    /**姓名*/
    public String personName="";
    /**保存个人资料后会返回*/
    public String mpiId="";
    /**性别代码，1*/
    public String sex="";
    /**出生日期  1990-01-01*/
    public String dob;
    /**手机号*/
    public String phoneNo="";
    /**头像记录号，1*/
    public String avatar="";
    /**民族代码，01*/
    public String nation="";
    /**国籍代码，CN*/
    public String nationality="";
    /**文化水平,01*/
    public String education="";
    /**收入水平,199000*/
    public String income="";
    /**血型代码,1*/
    public String bloodType="";
    /**RH血型代码,1*/
    public String rhBloodType="";
    /**婚姻状况代码,01*/
    public String maritalStatus="";
    /**医保类别代码,01*/
    public String insuranceType="";
    /**户籍类别代码,1*/
    public String houseHold="";
    /**省id，330000*/
    public String province="";
    /**市id,330100*/
    public String city="";
    /**区（县）id，330101*/
    public String district="";
    /**街道id，330101001*/
    public String street="";
    /**地址*/
    public String address="";
    public String provinceText = "";
    public String cityText = "";
    public String districtText = "";
    public String streetText = "";
    public String weight="";//体重
    public String height="";//身高
    public String profession="";//职业类别代码
    public String exerciseHabits="";//运动习惯
    public String drinkingHabits="";//饮酒习惯
    public String smokingHabits="";//吸烟习惯
    public String dietHabits="";//饮食习惯

    public String certified;// 0 未认证，1已认证

    public int age = -1;


    /**证件*/
    public CertificateVo certificate;
    
    
    public boolean hasAddress(){
        return !TextUtils.isEmpty(province) && !TextUtils.isEmpty(provinceText);
    }

    public String getCerTypeStr() {
        if(certificate == null) return "";
        if(!TextUtils.isEmpty(certificate.certificateTypeText)){
            return certificate.certificateTypeText;
        }
        return "";
    }

    public String getCerNo() {
        return certificate == null ? "" : StringUtil.notNull(certificate.certificateNo);
    }
    

    /**
     * 获取身份证str
     * @return
     */
    public String getIdcardStr(){
        if(certificate != null 
                && TextUtils.equals("01", certificate.certificateType)
                && !TextUtils.isEmpty(certificate.certificateNo)){
            return certificate.certificateNo;
            
        }
        return "";
    }
    
    public String getIdcardShowStr(){
        
        return "身份证-"+getIdcardStr();
    }

    public String getCardShowStr(){
        if(certificate != null){
            StringBuilder sb = new StringBuilder();
            sb.append(certificate.certificateTypeText).append("-")
                    .append(certificate.certificateNo);
            return sb.toString();

        }
        return "";
    }

    public boolean ifNeedGuardian(){
        return getAge() <= 15;
    }

    public String getSexValue() {
        return TextUtils.equals("1", sex) ? "男" : "女";
    }

    public void format(){

        if(!TextUtils.isEmpty(height)){
            if(height.contains(".")){
                height = String.valueOf((int) Float.parseFloat(height));
            }
        }else {
            if(!TextUtils.isEmpty(sex)){
                height = ("1".equals(sex)?"170":"160");
            }else {
                height = "170";
            }
        }

        if (!TextUtils.isEmpty(weight)){
            if(weight.contains(".")) {
                weight = String.valueOf((int) Float.parseFloat(weight));
            }
        }else {
            if(!TextUtils.isEmpty(sex)){
                weight = ("1".equals(sex)?"70":"50");
            }else {
                weight = "70";
            }
        }
    }

    public String getAddress() {
        StringBuffer sb = new StringBuffer();
        sb.append(provinceText).append(cityText).append(districtText)
                .append(streetText).append(address);
        return sb.toString();
    }
    
    public int getAge(){
        Date dateTime = DateUtil.getDateTime("yyyy-MM-dd", dob);
        int age = 0;
        if(dateTime != null) {
            age = DateUtil.getAge(dateTime.getTime());
        }
        return age > 0 ? age : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null||!(obj instanceof UserInfoVo)){return false;}
        UserInfoVo userInfoVo = (UserInfoVo) obj;

        if(!TextUtils.equals(userInfoVo.personName,personName)){
            return false;
        }
        if(!TextUtils.equals(userInfoVo.sex,sex)){
            return false;
        }
       
        if(!TextUtils.equals(userInfoVo.weight,weight)){
            return false;
        }
        if(!TextUtils.equals(userInfoVo.height,height)){
            return false;
        }
        if(!TextUtils.equals(userInfoVo.bloodType,bloodType)){
            return false;
        }
        if(!TextUtils.equals(userInfoVo.maritalStatus,maritalStatus)){
            return false;
        }
        if(!TextUtils.equals(userInfoVo.profession,profession)){
            return false;
        }
        if(!TextUtils.equals(userInfoVo.exerciseHabits,exerciseHabits)){
            return false;
        }
        if(!TextUtils.equals(userInfoVo.drinkingHabits,drinkingHabits)){
            return false;
        }
        if(!TextUtils.equals(userInfoVo.smokingHabits,smokingHabits)){
            return false;
        }
        if(!TextUtils.equals(userInfoVo.dietHabits,dietHabits)){
            return false;
        }
        if(!TextUtils.equals(userInfoVo.getAddress(),getAddress())){
            return false;
        }

        return true;
    }

    @Override
    public UserInfoVo clone(){
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.personName = personName;
        userInfoVo.mpiId = mpiId;
        userInfoVo.sex = sex;
        userInfoVo.dob = dob;
        userInfoVo.phoneNo = phoneNo;
        userInfoVo.avatar = avatar;
        userInfoVo.nation = nation;
        userInfoVo.nationality = nationality;
        userInfoVo.education = education;
        userInfoVo.income = income;
        userInfoVo.bloodType = bloodType;
        userInfoVo.rhBloodType = rhBloodType;
        userInfoVo.maritalStatus = maritalStatus;
        userInfoVo.insuranceType = insuranceType;
        userInfoVo.houseHold = houseHold;
        userInfoVo.province = province;
        userInfoVo.city = city;
        userInfoVo.district = district;
        userInfoVo.street = street;
        userInfoVo.address = address;
        userInfoVo.provinceText = provinceText;
        userInfoVo.cityText = cityText;
        userInfoVo.districtText = districtText;
        userInfoVo.streetText = streetText;
        userInfoVo.weight = weight;
        userInfoVo.height = height;
        userInfoVo.profession = profession;
        userInfoVo.exerciseHabits = exerciseHabits;
        userInfoVo.drinkingHabits = drinkingHabits;
        userInfoVo.smokingHabits = smokingHabits;
        userInfoVo.dietHabits = dietHabits;
        userInfoVo.certificate = certificate;
        return userInfoVo;
    }
}
