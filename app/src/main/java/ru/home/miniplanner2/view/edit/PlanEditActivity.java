package ru.home.miniplanner2.view.edit;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import ru.home.miniplanner2.R;
import ru.home.miniplanner2.model.Plan;

/**
 * Created by privod on 01.09.2016.
 */
public class PlanEditActivity extends EditActivity<Plan> {
    static final String LOG_TAG = PlanEditActivity.class.getSimpleName();

    private EditText nameEditText;
    private EditText dateRegEditText;

    @Override
    public Plan newInstanceEntity() {
        return new Plan();
    }

    @Override
    public void changeEntity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = (LinearLayout) findViewById(R.id.edit_content);
        getLayoutInflater().inflate(R.layout.edit_plan, layout, true);


    }
}
