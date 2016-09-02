package ru.home.miniplanner2.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.GregorianCalendar;

import ru.home.miniplanner2.R;
import ru.home.miniplanner2.db.Dao;
import ru.home.miniplanner2.db.HelperFactory;
import ru.home.miniplanner2.model.Plan;
import ru.home.miniplanner2.view.adapter.PlanAdapter;
import ru.home.miniplanner2.view.edit.PlanEditActivity;

public class PlansActivity extends AppCompatActivity {

    private Dao<Plan> planDao;
    PlanAdapter planAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HelperFactory.setHelper(this);
        planDao = HelperFactory.getHelper().getPlanDao();

        planAdapter = new PlanAdapter(PlanAdapter.PlanViewHolder.class);
        listView = (ListView) findViewById(R.id.list_view);
        if (null != listView) {
            listView.setAdapter(planAdapter);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (null != fab) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PlansActivity.this, PlanEditActivity.class);
                    intent.putExtra(getString(R.string.argument_id), 0L);
                    startActivityForResult(intent, getResources().getInteger(R.integer.request_code_plan_edit));
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        planAdapter.setData(planDao.getAll());
        planAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plans, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_cre_debug_data) {

            Dao<Plan> planDao = HelperFactory.getHelper().getPlanDao();

            Plan plan;
            plan = new Plan();
            plan.setName("Рыбылка");
            plan.setDateReg(new GregorianCalendar(2015, 5, 28).getTime());
            planDao.save(plan);

            plan = new Plan();
            plan.setName("Хмельники");
            plan.setDateReg(new GregorianCalendar(2015, 7, 2).getTime());
            planDao.save(plan);

            planAdapter.setData(planDao.getAll());
            planAdapter.notifyDataSetChanged();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
