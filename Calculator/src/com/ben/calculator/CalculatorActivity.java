package com.ben.calculator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorActivity extends ActionBarActivity {
		
	TextView resultsWindow;
	String currValue = "0";
	String oper = "";
	String preValue = "";
	String result;
	String parseString = "";
	boolean eqLastClicked = false;
	
	public static ArrayList<String> passedResult = new ArrayList<String>();
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);
		
		resultsWindow = (TextView)findViewById(R.id.result);
		resultsWindow.setText("0");
		
	    Log.d("CalcAct","Calculator Activity Created");
	}
	
	public void numberClicked(View sender) {
		Button clickedButton = (Button)sender;
		
		if(eqLastClicked) {
			preValue = "0";
			currValue = "0";
		}
		
		if (resultsWindow.getText().toString().contains(".")) {
			resultsWindow.append(clickedButton.getText());
		} else if (currValue.equals("0")) {
			resultsWindow.setText(clickedButton.getText());
		} else if (currValue.equals("-0")) {
			resultsWindow.setText("-" + clickedButton.getText());
		} else {
			resultsWindow.append(clickedButton.getText());
		}
		currValue = resultsWindow.getText().toString();
		eqLastClicked = false;
		return;			
	}
	
	public void signClicked(View sender) {
		if (currValue.contains("-")) {
			currValue = currValue.substring(1);
			resultsWindow.setText(currValue);
		} else {
			currValue = "-" + currValue;
			resultsWindow.setText(currValue);
		}
		eqLastClicked = false;
	}
	
	public void decClicked(View sender) {
		Button clickedButton = (Button)sender;
		
		if(!currValue.contains(".")) {
			resultsWindow.append(clickedButton.getText());
		}
		currValue = resultsWindow.getText().toString();
	}
	
	public void opClicked(View sender) {
		Button clickedButton = (Button)sender;
		oper = clickedButton.getText().toString();
		
		if(preValue.equals("") || preValue.equals("0")) {
			preValue = currValue;
		}
		currValue = "0";
		resultsWindow.setText(currValue);
		
		eqLastClicked = false;
	}
	
	public void clearClicked(View sender) {
		currValue = "0";
		preValue = "0";
		resultsWindow.setText(currValue);
	}
	
	public void eqClicked(View sender) {
		double val1 = 0d;
		double val2 = 0d;
		double result = 0d;
				
		val1 = Double.parseDouble(preValue);
		val2 = Double.parseDouble(currValue);
		
		if(!oper.equals("")) {
			
			if(oper.equals("*")) {
				result = (val1 * val2);
			}
			else if(oper.equals("/")) {
				result = (val1 / val2);
			}
			else if(oper.equals("+")) {
				result = (val1 + val2);
			}
			else if(oper.equals("-")) {
				result = (val1 - val2);
			}
			
			ParseObject CloudCalc = new ParseObject("CloudCalc");

			DecimalFormat df = new DecimalFormat("###.########");
			parseString = preValue + " " + oper + " " + currValue + " = " + df.format(result);
			CloudCalc.put("result",parseString);
			try {
				CloudCalc.save();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			preValue = df.format(result);
			resultsWindow.setText(preValue);
			eqLastClicked = true;
		}
	}
		
	public void resultsClicked(View view) {
		Intent myIntent = new Intent(CalculatorActivity.this, ResultsActivity.class);
		CalculatorActivity.this.startActivity(myIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculator, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
