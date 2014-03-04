package com.wlises.app.comm;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.widget.Toast;

public class IRHandler {
	
	private Activity activity;

    private Method write;
    private Object irdaService;
	
	public IRHandler(Activity activity) {
		this.activity = activity;
		
		this.init();
	}

	private void init() {

		try {
			irdaService = activity.getSystemService("irda");
			Class c = irdaService.getClass();
			Class p[] = { String.class };
			write = c.getMethod("write_irsend", p);
		} catch (Exception e) {
			Toast.makeText(activity, "Error :: " + e.toString(), Toast.LENGTH_LONG).show();
		}
	}

    /**
     *
     * @param entryButtonId
     * @return
     */
    public boolean send(String entryButtonId) {

        String irCode =
                activity.getResources().getString(
                        activity.getResources()
                                .getIdentifier(entryButtonId, "string", activity.getPackageName())
                );
        this.sendIR(irCode);

        return true;
    }

    /**
     *
     * @param irData
     */
    protected void sendIR(String irData) {
        try {
            write.invoke(irdaService, hex2dec(irData));
        } catch (Exception e) {
            Toast.makeText(activity, "Error :: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     *
     * @param irData
     * @return String
     */
	private String hex2dec(String irData) {
		List<String> list = new ArrayList<String>(Arrays.asList(irData
				.split(" ")));
		list.remove(0); // dummy
		int frequency = Integer.parseInt(list.remove(0), 16); // frequency
		list.remove(0); // seq1
		list.remove(0); // seq2

		for (int i = 0; i < list.size(); i++) {
			list.set(i, Integer.toString(Integer.parseInt(list.get(i), 16)));
		}

		frequency = (int) (1000000 / (frequency * 0.241246));
		list.add(0, Integer.toString(frequency));

		irData = "";
		for (String s : list) {
			irData += s + ",";
		}
		return irData;
	}

}
