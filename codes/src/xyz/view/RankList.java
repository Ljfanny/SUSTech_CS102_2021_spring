package xyz.view;

import xyz.database.DatabaseUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Enumeration;

public class RankList extends JFrame {
    RankList(String dif) {
        String[] token = dif.split(",");
        String mode = token[0];
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        String[] header =new String[1];
        DefaultTableModel defaultTableModel=new DefaultTableModel();
        if (token.length == 1) {
            header = new String[]{"Name", "ID", "Grade"};
            defaultTableModel = new DefaultTableModel(DatabaseUtil.rankList(dif), header);
        }else if (token[0].equals("players")){
            header = new String[]{"ID","NAME","LEVEL","EXP","WIN_RATE"};
            defaultTableModel = new DefaultTableModel(DatabaseUtil.rankList2(token[1]), header);
        }


        JTable table = new JTable(defaultTableModel);
        JScrollPane pane = new JScrollPane(table);
        table.setFont(new Font("微软雅黑",Font.PLAIN,20));
        table.setRowHeight(35);
        getContentPane().add(pane);
        FitTableColumns(table);
        setColumnColor(table);
    }

    public static void FitTableColumns(JTable myTable) {
        JTableHeader header = myTable.getTableHeader();
        int rowCount = myTable.getRowCount();

        Enumeration columns = myTable.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn column = (TableColumn) columns.nextElement();
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int) myTable.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(myTable, column.getIdentifier(), false, false, -1, col)
                    .getPreferredSize().getWidth();
            for (int row = 0; row < rowCount; row++) {
                int preferedWidth = (int) myTable.getCellRenderer(row, col)
                        .getTableCellRendererComponent(myTable, myTable.getValueAt(row, col), false, false, row, col)
                        .getPreferredSize().getWidth();
                width = Math.max(width, preferedWidth);
            }
            header.setResizingColumn(column);
            column.setWidth(width + myTable.getIntercellSpacing().width + 20);
        }
    }

    public static void setColumnColor(JTable table) {
        try {
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
                private static final long serialVersionUID = 1L;

                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    if (row % 2 == 0)
                        setBackground(new Color(243, 186, 2));//设置奇数行底色
                    else if (row % 2 == 1)
                        setBackground(new Color(201, 175, 166));//设置偶数行底色
                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
            };
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
            }
            tcr.setHorizontalAlignment(JLabel.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
