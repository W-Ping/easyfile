package com.ping.easyfile.util;

import com.ping.easyfile.em.DateTypeEnum;
import net.sf.cglib.beans.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liu_wp
 * @date Created in 2019/3/8 10:29
 */
public class TypeUtil {

    private static List<String> DATE_FORMAT_LIST = new ArrayList<>(4);

    static {
        DATE_FORMAT_LIST.add(DateTypeEnum.TIMESTAMP_TIME.getFormat());
        DATE_FORMAT_LIST.add(DateTypeEnum.TIMESTAMP_OBLIQUE_TIME.getFormat());
        DATE_FORMAT_LIST.add(DateTypeEnum.TIMESTAMP_NO_OBLIQUE_TIME.getFormat());
    }

    private static int getCountOfChar(String value, char c) {
        int n = 0;
        if (value == null) {
            return 0;
        }
        char[] chars = value.toCharArray();
        for (char cc : chars) {
            if (cc == c) {
                n++;
            }
        }
        return n;
    }

    public static Object convert(String value, Field field, String format, boolean us) {
        if (!StringUtils.isEmpty(value)) {
            if (Float.class.equals(field.getType())) {
                return Float.parseFloat(value);
            }
            if (Integer.class.equals(field.getType()) || int.class.equals(field.getType())) {
                return Integer.parseInt(value);
            }
            if (Double.class.equals(field.getType()) || double.class.equals(field.getType())) {
                if (null != format && !"".equals(format)) {
                    int n = getCountOfChar(value, '0');
                    return Double.parseDouble(TypeUtil.formatFloat0(value, n));
                } else {
                    return Double.parseDouble(TypeUtil.formatFloat(value));
                }
            }
            if (Boolean.class.equals(field.getType()) || boolean.class.equals(field.getType())) {
                String valueLower = value.toLowerCase();
                if (valueLower.equals("true") || valueLower.equals("false")) {
                    return Boolean.parseBoolean(value.toLowerCase());
                }
                Integer integer = Integer.parseInt(value);
                if (integer == 0) {
                    return false;
                } else {
                    return true;
                }
            }
            if (Long.class.equals(field.getType()) || long.class.equals(field.getType())) {
                return Long.parseLong(value);
            }
            if (Date.class.equals(field.getType())) {
                if (value.contains("-") || value.contains("/") || value.contains(":")) {
                    return TypeUtil.getSimpleDateFormatDate(value, format);
                } else {
                    Double d = Double.parseDouble(value);
                    return DateUtil.getJavaDate(d, us);
                }
            }
            if (BigDecimal.class.equals(field.getType())) {
                return new BigDecimal(value);
            }
            if (String.class.equals(field.getType())) {
                return formatFloat(value);
            }

        }
        return null;
    }

    public static Boolean isNum(Field field) {
        if (field == null) {
            return false;
        }
        if (Integer.class.equals(field.getType()) || int.class.equals(field.getType())) {
            return true;
        }
        if (Double.class.equals(field.getType()) || double.class.equals(field.getType())) {
            return true;
        }
        if (Float.class.equals(field.getType()) || float.class.equals(field.getType())) {
            return true;
        }
        if (Long.class.equals(field.getType()) || long.class.equals(field.getType())) {
            return true;
        }

        if (BigDecimal.class.equals(field.getType())) {
            return true;
        }
        return false;
    }

    public static Boolean isNum(Object cellValue) {
        if (cellValue instanceof Integer
                || cellValue instanceof Double
                || cellValue instanceof Short
                || cellValue instanceof Long
                || cellValue instanceof Float
                || cellValue instanceof BigDecimal) {
            return true;
        }
        return false;
    }

    public static String getDefaultDateString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);

    }

    public static Date getSimpleDateFormatDate(String value, String format) {
        if (!StringUtils.isEmpty(value)) {
            Date date = null;
            if (!StringUtils.isEmpty(format)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                try {
                    date = simpleDateFormat.parse(value);
                    return date;
                } catch (ParseException e) {
                }
            }
            for (String dateFormat : DATE_FORMAT_LIST) {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
                    date = simpleDateFormat.parse(value);
                } catch (ParseException e) {
                }
                if (date != null) {
                    break;
                }
            }

            return date;

        }
        return null;

    }


    public static String formatFloat(String value) {
        if (null != value && value.contains(".")) {
            if (isNumeric(value)) {
                try {
                    BigDecimal decimal = new BigDecimal(value);
                    BigDecimal setScale = decimal.setScale(10, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros();
                    return setScale.toPlainString();
                } catch (Exception e) {
                }
            }
        }
        return value;
    }

    public static String formatFloat0(String value, int n) {
        if (null != value && value.contains(".")) {
            if (isNumeric(value)) {
                try {
                    BigDecimal decimal = new BigDecimal(value);
                    BigDecimal setScale = decimal.setScale(n, BigDecimal.ROUND_HALF_DOWN);
                    return setScale.toPlainString();
                } catch (Exception e) {
                }
            }
        }
        return value;
    }

    public static final Pattern pattern = Pattern.compile("[\\+\\-]?[\\d]+([\\.][\\d]*)?([Ee][+-]?[\\d]+)?$");

    private static boolean isNumeric(String str) {
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String formatDate(Date cellValue, String format) {
        SimpleDateFormat simpleDateFormat;
        if (StringUtils.isNotBlank(format)) {
            simpleDateFormat = new SimpleDateFormat(format);
        } else {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        return simpleDateFormat.format(cellValue);
    }

    public static String formatDate(Timestamp cellValue, String format) {
        return new SimpleDateFormat(format).format(cellValue.getTime());
    }

    public static String formatDate(LocalDateTime cellValue, String format) {
        return cellValue.format(DateTimeFormatter.ofPattern(format));
    }

    public static String getFieldStringValue(BeanMap beanMap, String fieldName, String format) {
        String cellValue = null;
        Object value = beanMap.get(fieldName);
        if (value != null) {
            if (value instanceof Date) {
                cellValue = TypeUtil.formatDate((Date) value, format);
            } else if (value instanceof Timestamp) {
                cellValue = TypeUtil.formatDate((Timestamp) value, format);
            } else if (value instanceof LocalDateTime) {
                cellValue = TypeUtil.formatDate((LocalDateTime) value, format);
            } else {
                cellValue = value.toString();
            }
        }
        return cellValue;
    }

//    public static Map getFieldValues(List<String> stringList, ExcelHeadProperty excelHeadProperty, Boolean use1904WindowDate) {
//        Map map = new HashMap();
//        for (int i = 0; i < stringList.size(); i++) {
//            ExcelReadProperty columnProperty = excelHeadProperty.getExcelColumnProperty(i);
//            if (columnProperty != null) {
//                Object value = TypeUtil.convert(stringList.get(i), columnProperty.getField(),
//                    columnProperty.getFormat(), use1904WindowDate);
//                if (value != null) {
//                    map.put(columnProperty.getField().getName(),value);
//                }
//            }
//        }
//        return map;
//    }
}
