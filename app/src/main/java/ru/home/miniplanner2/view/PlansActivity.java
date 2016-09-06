package ru.home.miniplanner2.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.GregorianCalendar;
import java.util.List;

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

        planAdapter = new PlanAdapter(this, PlanAdapter.PlanViewHolder.class);
        listView = (ListView) findViewById(R.id.list_view);
        if (null != listView) {
            listView.setAdapter(planAdapter);

            listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new MultiChoiceModeBehavior() );

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

    /*void avatar_switch(View visible, View notVisible) {
        Animation animToSide = AnimationUtils.loadAnimation(this, R.anim.to_side);
        Animation animFromSide = AnimationUtils.loadAnimation(this, R.anim.from_side);

        animToSide.setAnimationListener(new AnimationAvatarBehavior(visible, notVisible, animFromSide));
        visible.startAnimation(animToSide);
    }*/

    class MultiChoiceModeBehavior implements AbsListView.MultiChoiceModeListener {

        @Override
        public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {


            /*View view = listView.getChildAt(position).findViewById(R.id.frame_layout_avatar);
//            view.setPivotX(0.5f);
            view.animate().withLayer().scaleX(0).setDuration(300).setListener(new AnimationAvatarBehavior(view, listView.getCheckedItemPositions()))
                    .scaleX(1)
                    .start();*/

                    /*ImageView avatarIcon = (ImageView) view.findViewById(R.id.icon_avatar);
                    ImageView checkedIcon = (ImageView) view.findViewById(R.id.icon_check);
                    if (checked) {
//                        animToSide.setAnimationListener(new AnimationAvatarBehavior(checkedIcon, avatarIcon));
//                        checkedIcon.startAnimation(animToSide);
                        avatar_switch(avatarcon, checkedIcon);
                    } else {
//                        animToSide.setAnimationListener(new AnimationAvatarBehavior(avatarIcon, checkedIcon));
//                        avatarIcon.startAnimation(animToSide);
                        avatar_switch(checkedIcon, vatarIcon);
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
                List<Plan> plans = planDao.getAll();
                SparseBooleanArray arrayChecked = listView.getCheckedItemPositions();
                for (int i = 0; i < arrayChecked.size(); i++) {
                    int position = arrayChecked.keyAt(i);
                    if (arrayChecked.get(position))
                        // TODO Падает при множественном удалении [исправолено]
                        planDao.delete(plans.get(position));
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
    }

    class AnimationAvatarBehavior extends AnimatorListenerAdapter {
        View view;
        SparseBooleanArray arrayChecked;

        public AnimationAvatarBehavior(View view, SparseBooleanArray arrayChecked) {
            this.view = view;
            this.arrayChecked = arrayChecked;
        }
        
        @Override
        public void onAnimationEnd(Animator animator) {
            planAdapter.setArrayChecked(arrayChecked);
            planAdapter.notifyDataSetChanged();
//            view.animate().withLayer().scaleX(1).setDuration(300).setListener(null)
//                    .start();
        }
    }
}
