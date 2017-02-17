package com.custom.view.activity.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;

import com.custom.view.R;
import com.custom.view.activity.base.BaseActivity;
import com.custom.view.view.autoscrollview.AutoHorizontalScrollTextView;
import com.custom.view.view.autoscrollview.AutoVerticalScrollTextView;
import com.custom.view.view.stepview.HorizontalStepView;
import com.custom.view.view.stepview.StepBean;
import com.custom.view.view.stepview.VerticalStepView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author https://github.com/baoyachi/StepView
 */
public class StepViewActivity extends BaseActivity {

    private VerticalStepView mSetpview0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepview);

        setTitle(AutoHorizontalScrollTextView.class.getSimpleName());

        this.init();
    }

    private void init() {
        HorizontalStepView stepView = (HorizontalStepView) findViewById(R.id.stepview);
        //-----------------------------this data is example and you can also get data from server-----------------------------
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("接单",1);
        StepBean stepBean1 = new StepBean("打包",1);
        StepBean stepBean2 = new StepBean("出发",0);
        StepBean stepBean3 = new StepBean("送单",-1);
        StepBean stepBean4 = new StepBean("完成",-1);
        StepBean stepBean5 = new StepBean("支付",-1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);
        stepsBeanList.add(stepBean4);
        stepsBeanList.add(stepBean5);
        //-----------------------------this data is example and you can also get data from server-----------------------------

        stepView.setStepViewTexts(stepsBeanList)
                .setTextSize(16)//set textSize
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getBaseContext(), android.R.color.white))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getBaseContext(), R.color.uncompleted_text_color))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(getBaseContext(), android.R.color.white))//设置StepsView text完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(getBaseContext(), R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.attention));//设置StepsViewIndicator AttentionIcon


        mSetpview0 = (VerticalStepView) findViewById(R.id.step_view0);

        List<String> list0 = new ArrayList<>();
        list0.add("您已提交定单，等待系统确认");
        list0.add("您的商品需要从外地调拨，我们会尽快处理，请耐心等待");
        list0.add("您的订单已经进入亚洲第一仓储中心1号库准备出库");
        list0.add("您的订单预计6月23日送达您的手中，618期间促销火爆，可能影响送货时间，请您谅解，我们会第一时间送到您的手中");
        list0.add("您的订单已打印完毕");
        list0.add("您的订单已拣货完成");
        list0.add("扫描员已经扫描");
        list0.add("打包成功");
        list0.add("您的订单在京东【华东外单分拣中心】发货完成，准备送往京东【北京通州分拣中心】");
        list0.add("您的订单在京东【北京通州分拣中心】分拣完成");
        list0.add("您的订单在京东【北京通州分拣中心】发货完成，准备送往京东【北京中关村大厦站】");
        list0.add("您的订单在京东【北京中关村大厦站】验货完成，正在分配配送员");
        list0.add("配送员【包牙齿】已出发，联系电话【130-0000-0000】，感谢您的耐心等待，参加评价还能赢取好多礼物哦");
        list0.add("感谢你在京东购物，欢迎你下次光临！");
        mSetpview0.setStepsViewIndicatorComplectingPosition(list0.size() - 2)//设置完成的步数
                .setStepViewTexts(list0)//总步骤
                .setTextSize(12)
                .setLinePaddingProportion(0.85f)//设置indicator线与线间距的比例系数
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, android.R.color.white))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.uncompleted_text_color))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(this, android.R.color.white))//设置StepsView text完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.attention));//设置StepsViewIndicator AttentionIcon
    }
}