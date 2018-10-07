package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import java.text.NumberFormat;

import static java.util.logging.Level.parse;

public class MainActivity extends AppCompatActivity {
    int numberOfOffees = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //This method is  when the plus button is clicked.
    public void increment(View view) {

        if (numberOfOffees == 50) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 50 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        numberOfOffees = numberOfOffees + 1;
        display(numberOfOffees);

    }

    //This method is  when the minus button is clicked.
    public void decrement(View view) {

        if (numberOfOffees == 0) {
            return;
        }
        numberOfOffees = numberOfOffees - 1;
        display(numberOfOffees);

    }

    /**
     * This method is displayplay the message when the button is click.
     */
    public void submitOrder(View view) {

        CheckBox WhippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippkedCream = WhippedCream.isChecked();

        CheckBox Chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = Chocolate.isChecked();

        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        String input = nameEditText.getText().toString();

        int price = calculatePrice(hasWhippkedCream, hasChocolate);

        String priceMessage = createOrderSummary(price, hasWhippkedCream, hasChocolate, input);
        displayMessage(priceMessage);
    }


    /**
     * Create summary of the order.
     *
     * @param price           of the order
     * @param addWhippedCream is whetter or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @return the  text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String input) {
        String priceMessage = "Name: " + input;
        priceMessage += "\nAdd whipped cream?: " + addWhippedCream;
        priceMessage += "\nAdd Chocolate? : " + addChocolate;
        priceMessage += "\nQuantity :" + numberOfOffees;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
//Price of 1 cup of offee
        int basePrice = 5;
        // add $1 if the user wants whipped cream
        if (addChocolate) {
            basePrice += 1;
        }
//add $2 if the user wants chocolate
        if (addWhippedCream) {
            basePrice += 2;
        }
        return basePrice * numberOfOffees;

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);

        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This two methods are called when email button is clicked.
     */
    public void EmailSubmitOrder(View view) {

        CheckBox WhippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippkedCream = WhippedCream.isChecked();

        CheckBox Chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = Chocolate.isChecked();

        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        String input = nameEditText.getText().toString();

        int price = calculatePrice(hasWhippkedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippkedCream, hasChocolate, input);

        byEmail(input, priceMessage);
    }

    public void byEmail(String input, String priceMessage) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this.
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just  Java order for " + input);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}
