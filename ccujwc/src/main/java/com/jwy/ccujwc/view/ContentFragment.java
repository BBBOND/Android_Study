package com.jwy.ccujwc.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jwy.ccujwc.R;

/**
 * Created by KIM on 2015/1/7.
 */
public class ContentFragment extends Fragment {

    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        textView = (TextView) view.findViewById(R.id.textView);

        String text = getArguments().getString("text");
        textView.setText(text);

        return view;
    }
}
