package com.example.moneysystem.mapper;

import com.example.moneysystem.constants.Constants;
import com.example.moneysystem.domain.SterlingCurrency;
import org.springframework.stereotype.Component;

@Component
public class MapperImpl implements Mapper{

    public SterlingCurrency toSterlingCurrency(String toConvert) {
        int pence = 0, shilling = 0, pound = 0;
        for(String s : toConvert.split(" ")) {
            int parsed = Integer.parseInt(s.substring(0, s.length() - 1));
            if(s.endsWith(Constants.PENCE_SUFFIX)) {
                pence = parsed;
            } else if(s.endsWith(Constants.SHILLING_SUFFIX)) {
                shilling = parsed;
            } else {
                pound = parsed;
            }
        }
        return new SterlingCurrency(pence, shilling, pound);
    }

    public String toString(SterlingCurrency toConvert) {
        StringBuilder sb = new StringBuilder();
        if(toConvert.getPound() != 0) {
            set(sb, toConvert.getPound(), Constants.POUND_SUFFIX);
        }
        if(toConvert.getShilling() != 0) {
            set(sb, toConvert.getShilling(), Constants.SHILLING_SUFFIX);
        }
        if(toConvert.getPence() != 0) {
            set(sb, toConvert.getPence(), Constants.PENCE_SUFFIX);
        }
        return sb.toString().trim();
    }

    public String toString(SterlingCurrency result, SterlingCurrency reminder) {
        String reminderConverted = toString(reminder);
        StringBuilder sb = new StringBuilder();
        if(!"".equals(reminderConverted)) {
            sb.append(" (");
            sb.append(reminderConverted);
            sb.append(")");
        }
        return toString(result) + sb;
    }

    private static void set(StringBuilder sb, int parsed, String suffix) {
        sb.append(" ");
        sb.append(parsed);
        sb.append(suffix);
    }
}
