package com.example.quickbalance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class QuickBalance extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final SharedPreferences sp = getSharedPreferences("mySharedPref", 0);
		String code = sp.getString("CODE", null);
		if(code == null){
			AlertDialog.Builder getCode = new AlertDialog.Builder(this);
			getCode.setTitle(R.string.title);
			getCode.setMessage(R.string.message);
			final EditText input = new EditText(this);
			getCode.setView(input);

			getCode.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  String value = input.getText().toString();
					SharedPreferences.Editor editor = sp.edit();
					editor.putString("CODE", value);
					editor.commit();
				  }
				});

			getCode.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int whichButton) {
				Toast t = Toast.makeText(getApplicationContext(), "BYE!", Toast.LENGTH_SHORT);
				t.show();
			  }
			});

			getCode.show();
		} else {
			Toast t = Toast.makeText(getApplicationContext(), Uri.encode(code), Toast.LENGTH_SHORT);
			t.show();
			code = "tel:" + code.replaceAll("#", "") + "%23";
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(code));
	        startActivity(intent);
	        finish();
		}
	}
}
