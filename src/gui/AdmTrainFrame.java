package gui;

import dao.TrainSQL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdmTrainFrame extends JFrame
{
    public static void main(String[] args)
    {
        new AdmTrainFrame();
    }

    public static final int DEFAULT_WIDTH = 600;
    public static final int DEFAULT_HEIGHT = 600;
    public static final String[] columnNames = {"车次", "列车类型", "出发时间", "到达时间",
            "运营时间", "经过天数", "始发站", "终点站", "平均速度"};
    private JTable trainTable;
    private JPanel northPanel;
    private JPanel southPanel;
    private JTextField field;
    private JButton queryButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton modifyButton;
    private JButton returnButton;

    public AdmTrainFrame()
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //居中显示
        setResizable(false);

        setTitle("列车管理");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        field = new JTextField(12);
        queryButton = new JButton("查找");
        queryButton.addActionListener(new QueryAction());
        northPanel = new JPanel();
        northPanel.add(field);
        northPanel.add(queryButton);

        trainTable = new JTable(TrainSQL.queryTrain(), columnNames);
        add(new JScrollPane(trainTable), BorderLayout.CENTER);
        trainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addButton = new JButton("增加列车");
        addButton.addActionListener(new AddAction());
        deleteButton = new JButton("删除列车");
        deleteButton.addActionListener(new DeleteAction());
        modifyButton = new JButton("查看经停站");
        modifyButton.addActionListener(new ModifyAction());
        returnButton = new JButton("返回上一级");
        returnButton.addActionListener(new ReturnAction());

        southPanel = new JPanel();
        southPanel.add(addButton);
        //southPanel.add(deleteButton);
        southPanel.add(modifyButton);
        southPanel.add(returnButton);

        //add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class QueryAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {

        }
    }

    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new AddTrainFrame();
            dispose();
        }
    }

    private class DeleteAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {

        }
    }

    private class ModifyAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int i = AdmTrainFrame.this.trainTable.getSelectedRow();
            if (i == -1)
            {
                JOptionPane.showMessageDialog(AdmTrainFrame.this, "请选择要查看的列车信息！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String triNo = AdmTrainFrame.this.trainTable.getValueAt(i, 0).toString();
            dispose();
            new TrainTransitFrame(triNo);
        }
    }

    private class ReturnAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dispose();
            new AdminFrame();
        }
    }
}
