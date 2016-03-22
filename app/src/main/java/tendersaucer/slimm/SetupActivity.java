package tendersaucer.slimm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * Created by Alex on 2/28/2016.
 *
 * http://examples.javacodegeeks.com/android/core/widget/viewflipper/android-viewflipper-example/
 */
public class SetupActivity extends Activity implements View.OnClickListener {

    private User user;
    private RadioGroup genderView;
    private RadioGroup measurementSystemView;
    private RadioGroup weightLossPaceView;
    private RadioGroup reminderFrequencyView;
    private EditText weightView;
    private EditText heightView;
    private DatePicker birthdateView;
    private Button backButton;
    private Button nextButton;
    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setup);

        user = User.getInstance();

        viewFlipper = (ViewFlipper)findViewById(R.id.setup_view_flipper);
        nextButton = (Button)findViewById(R.id.next_button);
        backButton = (Button)findViewById(R.id.back_button);
        genderView = (RadioGroup)findViewById(R.id.gender_radio_group);
        measurementSystemView = (RadioGroup)findViewById(R.id.measurement_system_radio_group);
        weightLossPaceView = (RadioGroup)findViewById(R.id.weight_loss_pace_radio_group);
        reminderFrequencyView = (RadioGroup)findViewById(R.id.reminder_frequency_radio_group);
        weightView = (EditText)findViewById(R.id.weight_edit_text);
        heightView = (EditText)findViewById(R.id.height_edit_text);
        birthdateView = (DatePicker)findViewById(R.id.birthdate_date_picker);

        backButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        toggleButton(backButton, false);
        toggleButton(nextButton, false);

        setInputCompletionListeners();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.back_button) {
            viewFlipper.showPrevious();
            toggleButton(backButton, getCurrPage() > 0);
            toggleButton(nextButton, true);
        } else if (id == R.id.next_button) {
            saveUserInput();

            if (getCurrPage() == viewFlipper.getChildCount() - 1) {
                completeSetup();
            } else {
                viewFlipper.showNext();
                toggleButton(backButton, getCurrPage() > 0);
                toggleButton(nextButton, false);
            }
        }
    }

    private void setInputCompletionListeners() {
        final RadioGroup.OnCheckedChangeListener radioGroupCompletionListener =
                getRadioGroupCompletionListener();
        genderView.setOnCheckedChangeListener(radioGroupCompletionListener);
        measurementSystemView.setOnCheckedChangeListener(radioGroupCompletionListener);
        weightLossPaceView.setOnCheckedChangeListener(radioGroupCompletionListener);
        reminderFrequencyView.setOnCheckedChangeListener(radioGroupCompletionListener);

        final EditText.OnEditorActionListener editTextCompletionListener =
                getEditTextCompletionListener();
        weightView.setOnEditorActionListener(editTextCompletionListener);
        heightView.setOnEditorActionListener(editTextCompletionListener);

        birthdateView.init(DateTime.now().getYear(), 0, 1, getDatePickerCompletionListener());
    }

    private void saveUserInput() {
        switch (getCurrPage()) {
            case 0:
                saveGender();
                break;
            case 1:
                saveBirthdate();
                break;
            case 2:
                saveSystemOfMeasurement();
                break;
            case 3:
                saveWeight();
                break;
            case 4:
                saveHeight();
                break;
            case 5:
                saveWeightLossPace();
                break;
            case 6:
                saveReminderFrequency();
                break;
        }
    }

    private void saveGender() {
        User.Gender gender = User.Gender.MALE;
        if (genderView.getCheckedRadioButtonId() == R.id.gender_male) {
            gender = User.Gender.FEMALE;
        }
        user.setGender(gender);
    }

    private void saveBirthdate() {
        int day = birthdateView.getDayOfMonth();
        int month = birthdateView.getMonth();
        int year =  birthdateView.getYear();
        LocalDate birthdate = new LocalDate(year, month + 1, day);

        user.setBirthdate(birthdate.toDate().getTime());
    }

    private void saveSystemOfMeasurement() {
        final TextView weighTextView = (TextView)findViewById(R.id.weight_text_view);
        final TextView heightTextView = (TextView)findViewById(R.id.height_text_view);

        if (measurementSystemView.getCheckedRadioButtonId() == R.id.measurement_system_metric) {
            user.setHeightUnit(MeasurementUnit.CENTIMETERS);
            user.setWeightUnit(MeasurementUnit.KILOGRAMS);

            weighTextView.setText("Weight (kg)");
            heightTextView.setText("Height (cm)");
        } else {
            user.setHeightUnit(MeasurementUnit.INCHES);
            user.setWeightUnit(MeasurementUnit.POUNDS);

            weighTextView.setText("Weight (lb)");
            heightTextView.setText("Height (in)");
        }
    }

    private void saveWeight() {
        float weightVal = Integer.parseInt(weightView.getText().toString());
        user.setCurrWeight(weightVal, user.getWeightUnit());
        user.setOrigWeight(weightVal, user.getWeightUnit());
    }

    private void saveHeight() {
        float heightVal = Integer.parseInt(heightView.getText().toString());
        user.setHeight(heightVal, user.getHeightUnit());
    }

    private void saveWeightLossPace() {
        float pace = 0;
        switch (weightLossPaceView.getCheckedRadioButtonId()) {
            case R.id.weight_loss_pace_1:
                pace = 0.5f;
                break;
            case R.id.weight_loss_pace_2:
                pace = 1;
                break;
            case R.id.weight_loss_pace_3:
                pace = 1.5f;
                break;
            case R.id.weight_loss_pace_4:
                pace = 2;
                break;
        }

        user.setWeightLossPace(pace, MeasurementUnit.POUNDS);
    }

    private void saveReminderFrequency() {
        User.ReminderFrequency frequency = User.ReminderFrequency.NEVER;
        int checkedId = reminderFrequencyView.getCheckedRadioButtonId();
        if (checkedId == R.id.reminder_frequency_2) {
            frequency = User.ReminderFrequency.WEEKLY;
        } else if (checkedId == R.id.reminder_frequency_3) {
            frequency = User.ReminderFrequency.DAILY;
        }

        user.setReminderFreq(frequency);
    }

    private void completeSetup() {
        user.setInitialized();

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void toggleButton(Button button, boolean on) {
        button.setEnabled(on);
        button.setVisibility(on ? View.VISIBLE : View.INVISIBLE);
    }

    private int getCurrPage() {
        return viewFlipper.indexOfChild(viewFlipper.getCurrentView());
    }

    private RadioGroup.OnCheckedChangeListener getRadioGroupCompletionListener() {
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                toggleButton(nextButton, true);
            }
        };
    }

    private EditText.OnEditorActionListener getEditTextCompletionListener() {
        return new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!v.getText().toString().isEmpty()) {
                    toggleButton(nextButton, true);
                }

                return true;
            }
        };
    }

    private DatePicker.OnDateChangedListener getDatePickerCompletionListener() {
        return new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                toggleButton(nextButton, true);
            }
        };
    }
}