package jobsfinder.writers.excel;

import org.apache.poi.ss.usermodel.CellStyle;

import java.util.List;
import java.util.Map;

public class ExcelRow {
    List<String> data;
    CellStyle style;

    public ExcelRow(List<String> data, CellStyle style)  {
        this.data = data;
        this.style = style;
    }

    public ExcelRow(List<String> data)  {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public CellStyle getStyle() {
        return style;
    }

    public void setStyle(CellStyle style) {
        this.style = style;
    }
}
