package ru.home.miniplanner2.view;

import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.GregorianCalendar;
import java.util.List;
import java.lang.Object;

import ru.home.miniplanner2.R;
import ru.home.miniplanner2.db.Dao;
import ru.home.miniplanner2.db.HelperFactory;
import ru.home.miniplanner2.model.Plan;
import ru.home.miniplanner2.view.adapter.BaseAdapter;
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

        planAdapter = new PlanAdapter(this, PlanAdapter.PlanViewHolder.class);
        listView = (ListView) findViewById(R.id.list_view);
        if (null != listView) {
            listView.setAdapter(planAdapter);

            listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
//                    Toast.makeText(PlansActivity.this, "Action mode: " + checked, Toast.LENGTH_SHORT).show();

                    View view = listView.getChildAt(position).findViewById(R.id.frame_layout_avatar);
//                    view.setPivotX(0.5f);
                    view.animate().withLayer().scaleX(0).setDuration(300).start();

                    /*ImageView avatar = (ImageView) view.findViewById(R.id.avatar);
                    ImageView iconChecked = (ImageView) view.findViewById(R.id.check_icon);
                    if (checked) {
//                        animToSide.setAnimationListener(new AvatarAnimationListener(iconChecked, avatar));
//                        iconChecked.startAnimation(animToSide);
                        avatar_switch(avatar, iconChecked);
                    } else {
//                        animToSide.setAnimationListener(new AvatarAnimationListener(avatar, iconChecked));
//                        avatar.startAnimation(animToSide);
                        avatar_switch(iconChecked, avatar);
                    }*/


//                    planAdapter.notifyDataSetChanged();
                }

                @Override
                public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                    mode.getMenuInflater().inflate(R.menu.action_mode, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                    if (item.getItemId() == R.id.action_remove) {
//                        List<Plan> plans = planDao.getAll();
                        SparseBooleanArray arrayChecked = listView.getCheckedItemPositions();
                        for (int i = 0; i < arrayChecked.size(); i++) {
                            int position = arrayChecked.keyAt(i);
                            if (arrayChecked.get(position))
                                // TODO Падает при множественном удалении
                                planDao.delete(planDao.getAll().get(position));
                        }
                        planAdapter.setData(planDao.getAll());
                        planAdapter.notifyDataSetChanged();

                        mode.finish();
//                        multiSelector.clearSelections();
                        return true;
                    } else if (item.getItemId() == R.id.action_edit) {
                        int position = listView.getCheckedItemPosition();
                        startPlanEditActivity(planDao.getAll().get(position).getId());
//                        openPlanEditActivity(listView.getCheckedItemPosition());       // Режим редактирования возможен только если выцделен один элемен, поэтому цикла не делаю, а выбираю нулевой элемент.
                    }
                    return false;
                }

                @Override
                public void onDestroyActionMode(android.view.ActionMode mode) {

                }
            });

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (null != fab) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startPlanEditActivity(0L);
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

    private void startPlanEditActivity(long planId) {
        Intent intent = new Intent(PlansActivity.this, PlanEditActivity.class);
        intent.putExtra(getString(R.string.argument_id), planId);
        startActivityForResult(intent, getResources().getInteger(R.integer.request_code_plan_edit));
    }

    void avatar_switch(View visible, View notVisible) {
        Animation animToSide = AnimationUtils.loadAnimation(this, R.anim.to_side);
        Animation animFromSide = AnimationUtils.loadAnimation(this, R.anim.from_side);

        animToSide.setAnimationListener(new AvatarAnimationListener(visible, notVisible, animFromSide));
        visible.startAnimation(animToSide);
    }

    class AvatarAnimationListener implements Animation.AnimationListener {
        View view;
        Animation animNext;

        public AvatarAnimationListener(View view, Animation animNext) {
            this.view = view;
            this.animNext = animNext;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            visible.setVisibility(View.GONE);
            notVisible.setVisibility(View.VISIBLE);
            notVisible.startAnimation(animNext);
            animNext.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    planAdapter.notifyDataSetChanged();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        /*class AvatarAnimationListener implements Animation.AnimationListener {
        View visible;
        View notVisible;
        Animation animNext;

        public AvatarAnimationListener(View visible, View notVisible, Animation animNext) {
            this.visible = visible;
            this.notVisible = notVisible;
            this.animNext = animNext;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            visible.setVisibility(View.GONE);
            notVisible.setVisibility(View.VISIBLE);
            notVisible.startAnimation(animNext);
            animNext.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    planAdapter.notifyDataSetChanged();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }*/
    }
}
