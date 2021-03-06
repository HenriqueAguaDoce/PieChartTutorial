package pt.ipbeja.pdm2.piecharttutorial;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static  String TAG = "MainActivity";
    private  float[] yData = {25.3f, 10.6f, 66.76f, 44.32f, 44.01f, 16.89f, 23.9f};
    private  String[] xData = {"Pedro", "João", "Henrique", "Daniela", "Bento", "Xico", "Bruno"};
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Starting to create chart");

        pieChart = (PieChart) findViewById(R.id.idPieChart);
        pieChart.getDescription().setText("");
        pieChart.setBackgroundColor(Color.TRANSPARENT);
        pieChart.setRotationEnabled(false);
        pieChart.setRotationAngle(180f);
        pieChart.setUsePercentValues(true);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setTransparentCircleColor(0);
        pieChart.setHoleColor(0);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setMaxAngle(180f);
        //pieChart.setCenterTextOffset(0, -40);
        pieChart.setCenterText("Chart");
        pieChart.setCenterTextSize(10);
        pieChart.getLegend().setEnabled(false);
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pieChart.setElevation(15f);
        }

        addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());


                int pos = e.toString().indexOf("y: ");
                String sales = e.toString().substring(pos + 3);

                for(int i = 0; i < yData.length; i++){

                    if(yData[i] == Float.parseFloat(sales)){
                        pos = i;
                        break;
                    }
                }
                String employee = xData[pos];
                Log.d(TAG, "xData: " + xData[pos]);
                Log.d(TAG, "pos: " + (pos));
                Toast.makeText(MainActivity.this, "Employee " + employee + "\n" + "Sales: $" + sales + "K", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Employee Sales");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
