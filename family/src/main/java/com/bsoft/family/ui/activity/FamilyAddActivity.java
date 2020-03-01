package com.bsoft.family.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bsoft.family.R;
import com.bsoft.family.databinding.FamilyActivityFamilyAddBinding;
import com.bsoft.family.model.ShowMode;
import com.bsoft.libbasic.base.activity.CoreActivity;
import com.bsoft.libbasic.utils.EffectUtil;
import com.bsoft.libbasic.utils.ToastUtil;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libcommon.common.dicchoice.MultiChoiceActivity;
import com.bsoft.libcommon.common.dicchoice.SingleChoiceActivity;
import com.bsoft.libcommon.common.dicchoice.model.ChoiceItem;
import com.bsoft.libcommon.common.dicchoice.model.ResultItem;
import com.bsoft.libcommon.commonaop.SingleClick;
import com.bsoft.libcommon.dictionary.CardTypeDic;
import com.bsoft.libcommon.dictionary.NationalDic;
import com.bsoft.libcommon.dictionary.RelationDic;
import com.bsoft.libcommon.dictionary.SexDic;
import com.bsoft.libcommon.model.CertificateVo;
import com.bsoft.libcommon.model.FamilyVo;
import com.bsoft.libcommon.utils.CommonUtil;
import com.bsoft.libcommon.utils.DateUtil;
import com.bsoft.libcommon.utils.FilterEmoji;
import com.bsoft.libcommon.utils.IDCard;
import com.bsoft.libcommon.widget.stepview.bean.StepBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created on 2019/6/11 14:04
 *
 * @author cyf
 * @des :
 */

@Route(path = CommonArouterGroup.FAMILY_ADD_ACTIVITY)
public class FamilyAddActivity extends CoreActivity {
    /*Default*/

    public static final int ACTIVITY_RESULT_NATIONAL = 100;
    public static final int ACTIVITY_RESULT_CARD = 101;
    public static final int ACTIVITY_RESULT_SEX = 102;
    public static final int ACTIVITY_RESULT_RELATION = 103;
    /*Util*/
    /*Flag*/
//    private String mpiId;
//    private String relation;
    private ShowMode showMode;
    private FamilyVo familyVo = new FamilyVo();
    /*View*/
    private FamilyActivityFamilyAddBinding binding;

    public static void appStart() {
        CommonArouterGroup.getArouter(CommonArouterGroup.FAMILY_ADD_ACTIVITY)
                .navigation();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.family_activity_family_add);
        initTopBar();
        setListener();
        showMode.setAdd(true);
        initDefaultRelation();

    }

    //默认其他
    private void initDefaultRelation() {
        if (familyVo != null && TextUtils.isEmpty(familyVo.relation)) {
            familyVo.relation = RelationDic.OTHER;
        }
        binding.tvRelation.setText(RelationDic.getIdcardText(familyVo.relation));
    }


    protected void initTopBar() {
        super.initTopBar();

        showMode = new ShowMode();
        binding.setShowMode(showMode);

        initStepView();
        //初始化
        setView(null);
    }

    @Override
    public void onBackPressed() {
        if (showMode.getStep() == 0) {
            super.onBackPressed();
        } else if (showMode.getStep() == 1) {
            showMode.setStep(0);
            stepBean0.setState(StepBean.STEP_CURRENT);
            stepBean1.setState(StepBean.STEP_UNDO);
            binding.stepView.notifyDataChanged();
        } else {
            super.onBackPressed();
        }
    }

    final List<StepBean> stepsBeanList = new ArrayList<>();
    final StepBean stepBean0 = new StepBean("证件验证", StepBean.STEP_CURRENT);
    final StepBean stepBean1 = new StepBean("信息完善", StepBean.STEP_UNDO);

    private void initStepView() {

        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);

//        binding.stepView.setStepViewTexts(stepsBeanList)
//                .setTextSize(16)//set textSize
//                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, R.color.main_green))//设置StepsViewIndicator完成线的颜色
//                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.color_333))//设置StepsViewIndicator未完成线的颜色
//                .setStepViewComplectedTextColor(ContextCompat.getColor(this, R.color.main_green))//设置StepsView text完成的颜色
//                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.color_666))//设置StepsView text未完成线的颜色
//                .setStepsViewIndicatorCircleRadius(DensityUtil.dip2px(12))
//                .setStepsViewIndicatorLineHeight(DensityUtil.dip2px(4))
//                .setStepsViewIndicatorLinePadding(DensityUtil.dip2px(140))
//                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.icon_checked_p))//设置StepsViewIndicator CompleteIcon
////                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
//                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.icon_checked_p));//设置StepsViewIndicator AttentionIcon


                binding.stepView
                        .setStepViewTexts(stepsBeanList)//总步骤
                .setTextSize(14)//set textSize
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, android.R.color.white))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.black))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(this, android.R.color.white))//设置StepsView text完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.attention));//设置StepsViewIndicator AttentionIcon
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ACTIVITY_RESULT_NATIONAL == requestCode) {
            if (resultCode == RESULT_OK && data != null) {
                ChoiceItem curCard = (ChoiceItem) data.getSerializableExtra(SingleChoiceActivity.INTENT_RESULT);
                if (familyVo != null) {
                    if (familyVo.certificate == null) {
                        familyVo.certificate = new CertificateVo();
                    }
                    familyVo.certificate.source = curCard.getIndex();
                    familyVo.certificate.sourceText = curCard.getItemName();

                    binding.tvCountry.setText(familyVo.certificate.sourceText);
                }
            }
        } else if (ACTIVITY_RESULT_CARD == requestCode) {
            if (resultCode == RESULT_OK && data != null) {
                ChoiceItem curCard = (ChoiceItem) data.getSerializableExtra(SingleChoiceActivity.INTENT_RESULT);
                if (familyVo != null) {
                    if (familyVo.certificate == null) {
                        familyVo.certificate = new CertificateVo();
                    }
                    familyVo.certificate.certificateType = curCard.getIndex();
                    familyVo.certificate.certificateTypeText = curCard.getItemName();

                    binding.tvCardType.setText(familyVo.certificate.certificateTypeText);
                }
            }
        } else if (ACTIVITY_RESULT_SEX == requestCode) {
            if (resultCode == RESULT_OK && data != null) {
                ChoiceItem curCard = (ChoiceItem) data.getSerializableExtra(SingleChoiceActivity.INTENT_RESULT);
                if (familyVo != null) {
                    familyVo.sex = curCard.getIndex();

                    binding.tvSex.setText(SexDic.getSexStr(familyVo.sex));
                }
            }
        } else if (ACTIVITY_RESULT_RELATION == requestCode) {
            if (resultCode == RESULT_OK && data != null) {
                ChoiceItem curCard = (ChoiceItem) data.getSerializableExtra(SingleChoiceActivity.INTENT_RESULT);
                if (familyVo != null) {
                    familyVo.relation = curCard.getIndex();

                    binding.tvRelation.setText(RelationDic.getIdcardText(familyVo.relation));
                }
            }
        }

    }

    private boolean validate(int step) {
        if (familyVo == null) {
            return false;
        }
        if (step == 0) {
            if (familyVo.certificate == null || TextUtils.isEmpty(familyVo.certificate.source)) {
                ToastUtil.toast("请选择国籍");
                return false;
            } else if (TextUtils.isEmpty(familyVo.certificate.certificateType)) {
                ToastUtil.toast("请选择证件类型");
                return false;
            } else if (TextUtils.isEmpty(familyVo.certificate.certificateNo)) {
                ToastUtil.toast("请填写对应证件号");
                return false;
            } else {
                return true;
            }
        } else if (step == 1) {
            if (TextUtils.isEmpty(familyVo.personName)) {
                ToastUtil.toast("请填写姓名");
                return false;
            } else if (TextUtils.isEmpty(familyVo.sex)) {
                ToastUtil.toast("请选择性别");
                return false;
            } else if (TextUtils.isEmpty(familyVo.dob)) {
                ToastUtil.toast("请选择生日");
                return false;
            } else if (TextUtils.isEmpty(familyVo.phoneNo) || !CommonUtil.validatePhone(familyVo.phoneNo)) {
                ToastUtil.toast("请填写手机号");
                return false;
            } else if (TextUtils.isEmpty(familyVo.relation)) {
                ToastUtil.toast("请选择关系类型");
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }


    private void setListener() {
        //提交
        EffectUtil.addClickEffect(binding.tvUpload);
        binding.tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            @SingleClick
            public void onClick(View v) {
                if (showMode.getStep() == 0 && validate(0)) {
                    taskCheckInfo();
                } else if (showMode.getStep() == 1 && validate(1)) {
                    saveInfo(familyVo);
                }
            }
        });


        //姓名
        binding.etName.addTextChangedListener(new FilterEmoji(binding.etName, this));
        binding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (familyVo != null) {
                    familyVo.personName = s.toString();
                }
            }
        });

        //证件号码
        binding.etCard.addTextChangedListener(new FilterEmoji(binding.etCard, this));
        binding.etCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (familyVo != null) {
                    if (familyVo.certificate == null) {
                        familyVo.certificate = new CertificateVo();
                    }

                    familyVo.certificate.certificateNo = s.toString();

                    //根据证件号码更新性别和生日
                    if (TextUtils.equals(familyVo.certificate.certificateType, CardTypeDic.IDCARD)) {
                        //性别
                        String sex = IDCard.getSex(s.toString());
                        if (!TextUtils.isEmpty(sex)) {
                            familyVo.sex = sex;
                            binding.tvSex.setText(SexDic.getSexStr(familyVo.sex));
                        }
                        //生日
                        Long date = IDCard.getBirthDay(s.toString());
                        if (date != null) {
                            familyVo.dob = DateUtil.getDateTime(DateUtil.PATTERN, date);
                            binding.tvBirth.setText(DateUtil.formatDateStr(familyVo.dob, DateUtil.PATTERN, DateUtil.PATTERN3));
                        }

                    }
                }
            }
        });

        //手机
        binding.etTel.addTextChangedListener(new FilterEmoji(binding.etTel, this));
        binding.etTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (familyVo != null) {
                    familyVo.phoneNo = s.toString();
                }
            }
        });


        //国籍
            EffectUtil.addClickEffect(binding.rlCountry);
            binding.rlCountry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<ChoiceItem> items = NationalDic.getChoice();
                    ChoiceItem curCard = null;
                    if (familyVo != null && familyVo.certificate != null) {
                        curCard = new ChoiceItem(familyVo.certificate.source, familyVo.certificate.sourceText);
                    }
//                    SingleChoiceActivity.appStart(FamilyAddActivity.this
//                            , getString(R.string.wise_common_national)
//                            , items, curCard, ACTIVITY_RESULT_NATIONAL);

                    MultiChoiceActivity.appStart(FamilyAddActivity.this
                            , getString(R.string.wise_common_national)
                            , items, new ResultItem(), ACTIVITY_RESULT_NATIONAL);


                }
            });

        //证件类型
            EffectUtil.addClickEffect(binding.rlCardType);
            binding.rlCardType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<ChoiceItem> items = CardTypeDic.getChoice();
                    ChoiceItem curCard = null;
                    if (familyVo != null && familyVo.certificate != null) {
                        curCard = new ChoiceItem(familyVo.certificate.certificateType, familyVo.certificate.certificateTypeText);
                    }
                    SingleChoiceActivity.appStart(FamilyAddActivity.this
                            , getString(R.string.wise_common_card)
                            , items, curCard, ACTIVITY_RESULT_CARD);
                }
            });

        //2019.6.28 改为不能修改
//        if (CoreConstants.isDebug) {
//           //国籍
//            EffectUtil.addClickEffect(binding.rlCountry);
//            binding.rlCountry.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ArrayList<ChoiceItem> items = NationalDic.getChoice();
//                    ChoiceItem curCard = null;
//                    if (familyVo != null && familyVo.certificate != null) {
//                        curCard = new ChoiceItem(familyVo.certificate.source, familyVo.certificate.sourceText);
//                    }
//                    SingleChoiceActivity.appStart(activity
//                            , getString(R.string.wise_common_national)
//                            , items, curCard, ACTIVITY_RESULT_NATIONAL, ThemeConfigUtil.getTheme());
//                }
//            });
            //证件类型
//            EffectUtil.addClickEffect(binding.rlCardType);
//            binding.rlCardType.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ArrayList<ChoiceItem> items = CardTypeDic.getChoice();
//                    ChoiceItem curCard = null;
//                    if (familyVo != null && familyVo.certificate != null) {
//                        curCard = new ChoiceItem(familyVo.certificate.certificateType, familyVo.certificate.certificateTypeText);
//                    }
//                    SingleChoiceActivity.appStart(activity
//                            , getString(R.string.wise_common_card)
//                            , items, curCard, ACTIVITY_RESULT_CARD, ThemeConfigUtil.getTheme());
//                }
//            });
//        }
        //性别
        EffectUtil.addClickEffect(binding.rlSex);
        binding.rlSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ChoiceItem> items = SexDic.getChoice();
                ChoiceItem curCard = null;
                if (familyVo != null) {
                    curCard = new ChoiceItem(familyVo.sex, null);
                }
                SingleChoiceActivity.appStart(FamilyAddActivity.this
                        , getString(R.string.wise_common_sex)
                        , items, curCard, ACTIVITY_RESULT_SEX);
            }
        });
        //家庭关系
        EffectUtil.addClickEffect(binding.rlRelation);
        binding.rlRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ChoiceItem> items = RelationDic.getChoice(false);
                ChoiceItem curCard = null;
                if (familyVo != null) {
                    curCard = new ChoiceItem(familyVo.relation, null);
                }
                SingleChoiceActivity.appStart(FamilyAddActivity.this
                        , getString(R.string.wise_common_relation)
                        , items, curCard, ACTIVITY_RESULT_RELATION);
            }
        });

        //生日
        EffectUtil.addClickEffect(binding.rlBirth);
//        binding.rlBirth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar calendar = Calendar.getInstance();
//                if (familyVo != null && !TextUtils.isEmpty(familyVo.dob)) {
//                    Date date = DateUtil.getDateTime(DateUtil.PATTERN, familyVo.dob);
//                    calendar.setTimeInMillis(date.getTime());
//                }
//                int curYear = calendar.get(Calendar.YEAR);
//                int curMonth = calendar.get(Calendar.MONTH);
//                int curDay = calendar.get(Calendar.DAY_OF_MONTH);
//                CoreDatePickerDialog.newInstance(curYear, curMonth, curDay)
//                        .setMaxDate(DateUtil.getCurTime())
//                        .setCommonDialogListener(new CoreDatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                Calendar cur = Calendar.getInstance();
//                                cur.set(Calendar.YEAR, year);
//                                cur.set(Calendar.MONTH, monthOfYear - 1);
//                                cur.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                                if (familyVo != null) {
//                                    familyVo.dob = DateUtil.getDateTime(DateUtil.PATTERN, cur.getTimeInMillis());
//
//                                    binding.tvBirth.setText(DateUtil.formatDateStr(familyVo.dob, DateUtil.PATTERN, DateUtil.PATTERN3));
//                                }
//                            }
//                        }).show(getFragmentManager(), "");
//            }
//        });
    }

    private void setView(FamilyVo vo) {
        familyVo = vo;
        if (familyVo == null) {
            familyVo = new FamilyVo();
        }


        //默认身份证type
        if (familyVo.certificate == null) {
            familyVo.certificate = new CertificateVo();
            familyVo.certificate.source = NationalDic.CHINA;
            familyVo.certificate.sourceText = NationalDic.getIdcardText(NationalDic.CHINA);
            familyVo.certificate.certificateType = CardTypeDic.IDCARD;
            familyVo.certificate.certificateTypeText = CardTypeDic.getIdcardText(CardTypeDic.IDCARD);
        }


        binding.etName.setText(familyVo.personName);
        if (familyVo.certificate != null) {
            binding.tvCountry.setText(familyVo.certificate.sourceText);
            binding.tvCardType.setText(familyVo.certificate.certificateTypeText);
            binding.etCard.setText(familyVo.certificate.certificateNo);
        }

        binding.tvSex.setText(SexDic.getSexStr(familyVo.sex));
        binding.tvBirth.setText(DateUtil.formatDateStr(familyVo.dob, DateUtil.PATTERN, DateUtil.PATTERN3));
        binding.etTel.setText(familyVo.phoneNo);
        if(TextUtils.isEmpty(familyVo.relation)){
            familyVo.relation = RelationDic.OTHER;
        }
        binding.tvRelation.setText(RelationDic.getIdcardText(familyVo.relation));
    }

    private void taskCheckInfo() {

















//        if (!AccountSharpref.getInstance().getLoginState()) {
//            LogUtil.e("not login");
//            return;
//        }
//
//
//        ArrayMap<String, String> head = new ArrayMap<>();
//        head.put(ConstantsHttp.Head_Token, AccountSharpref.getInstance().getAccessToken());
//        head.put(ConstantsHttp.Head_Id, ConstantsHttp.Family_Service);
//        head.put(ConstantsHttp.Head_Method, "checkFamilyMember");
//
//
//        ArrayList body = new ArrayList();
//        body.add(getCheckParams(familyVo));
//
//        CommonPostManager.post(activity, head, body, FamilyVo.class,
//                new BaseObserver<FamilyVo>() {
//                    @Override
//                    public void onHandlePrePare(Disposable disposable) {
//                        showLoadingDialog();
//                    }
//
//                    @Override
//                    protected void onHandleSuccess(FamilyVo value) {
//                        showMode.setStep(1);
//                        stepBean0.setState(StepBean.STEP_COMPLETED);
//                        stepBean1.setState(StepBean.STEP_CURRENT);
//                        binding.stepView.notifyDataChanged();
//                        if (value != null) {
//                            restoreView();
//                            setView(value);
//                            showMode.setAdd(!(value.ifRegister() && !TextUtils.isEmpty(value.mpiId)));
//                        } else {
//                            restoreView();
//                            CertificateVo certificate = familyVo.certificate;
//                            familyVo = new FamilyVo();
//                            familyVo.certificate = certificate;
//
//                            setView(familyVo);
//                            showMode.setAdd(true);
//                        }
//                    }
//
//                    @Override
//                    protected void onHandleError(String errorType, String msg) {
//                        showErrorToast(errorType, msg);
//                    }
//
//                    @Override
//                    protected void onHandleComplete() {
//                        dismissLoadingDialog();
//                    }
//                });
    }

//    private ArrayMap<String, String> getCheckParams(FamilyVo vo) {
//        ArrayMap<String, String> map1 = new ArrayMap<String, String>();
//        if (vo.certificate != null) {
//            map1.put("certificateNo", vo.certificate.certificateNo);
//            map1.put("certificateType", vo.certificate.certificateType);
////                map1.put("certificateTypeText",vo.certificate.certificateTypeText);
//            map1.put("source", vo.certificate.source);
////                map1.put("sourceText",vo.certificate.sourceText);
//        }
//        return map1;
//    }

    private void saveInfo(final FamilyVo familyVo) {
//        if (!AccountSharpref.getInstance().getLoginState()) {
//            LogUtil.e("not login");
//            return;
//        }
//        if (familyVo == null) {
//            LogUtil.e("familyVo null");
//            return;
//        }
//
//        ArrayMap<String, String> head = new ArrayMap<>();
//        head.put(ConstantsHttp.Head_Token, AccountSharpref.getInstance().getAccessToken());
//        head.put(ConstantsHttp.Head_Id, ConstantsHttp.Family_Service);
//        head.put(ConstantsHttp.Head_Method, "saveFamilyMember");
//
//        ArrayList<Object> body = getParms(familyVo);
//
//        CommonPostManager.post(activity, head, body, String.class,
//                new BaseObserver<String>() {
//                    @Override
//                    public void onHandlePrePare(Disposable disposable) {
//                        showLoadingDialog();
//                    }
//
//                    @Override
//                    protected void onHandleSuccess(String value) {
//                        EventBus.getDefault().post(new FamilyInfoEvent());
//                        //通知sdk添加就诊人完成，深圳sdk是跳转主项目的添加就诊人
//                        InterHosLogin.notifyAddFamilyCompleted(JSON.toJSONString(familyVo));
//                        finish();
//                    }
//
//                    @Override
//                    protected void onHandleError(String errorType, String msg) {
//                        showErrorToast(errorType, msg);
//                    }
//
//                    @Override
//                    protected void onHandleComplete() {
//                        dismissLoadingDialog();
//                    }
//                });
    }

    private ArrayList<Object> getParms(FamilyVo vo) {
        ArrayList<Object> list = new ArrayList<Object>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("personName", vo.personName);
        map.put("mpiId", vo.mpiId);
        map.put("sex", vo.sex);
        map.put("dob", vo.dob);
        map.put("phoneNo", vo.phoneNo);
        map.put("avatar", vo.avatar);

        if (vo.certificate != null) {
            map.put("nationality", vo.certificate.source);
            HashMap<String, String> map1 = new HashMap<String, String>();
            map1.put("certificateNo", vo.certificate.certificateNo);
            map1.put("certificateType", vo.certificate.certificateType);
            map1.put("source", vo.certificate.source);
            map.put("certificate", map1);
        }
        list.add(map);
        list.add(vo.relation);
        return list;
    }


}
