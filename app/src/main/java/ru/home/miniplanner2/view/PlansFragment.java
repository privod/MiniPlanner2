package ru.home.miniplanner2.view;

import android.app.ListFragment;
import android.os.Bundle;

import ru.home.miniplanner2.db.Dao;
import ru.home.miniplanner2.db.HelperFactory;
import ru.home.miniplanner2.model.Plan;
import ru.home.miniplanner2.view.adapter.PlanAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlansFragment extends ListFragment {

    private Dao<Plan> planDao;

    public PlanAdapter getPlanAdapter() {
        return planAdapter;
    }

    private PlanAdapter planAdapter;

//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//    @Override
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_plans, container, false);
//    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        planDao = HelperFactory.getHelper().getPlanDao();

        planAdapter = new PlanAdapter(PlanAdapter.PlanViewHolder.class);
        setListAdapter(planAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        planAdapter.setData(planDao.getAll());
        planAdapter.notifyDataSetChanged();
    }
}
