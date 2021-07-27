package gui;

import dao.StationSQL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StationFrame extends JFrame
{
    public static void main(String[] args)
    {
        new StationFrame();
    }
    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 400;
    public static final String[] columnNames = {"��վ��", "����", "γ��"};
    private JTable stationTable;
    private JPanel buttonPanel;
    private JButton confirmButton;
    private JButton returnButton;
    private int flag = 0;       // ��־ѡ����ǳ���վ����Ŀ��վ
    private UserFrame caller = null;

    public StationFrame()
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //������ʾ
        setResizable(false);

        setTitle("ѡ��վ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        stationTable = new JTable(StationSQL.queryStation(), columnNames);
        add(new JScrollPane(stationTable), BorderLayout.CENTER);
        stationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        buttonPanel = new JPanel();
        confirmButton = new JButton("ѡ��վ");
        confirmButton.addActionListener(new SelectAction());
        returnButton = new JButton("������һ��");
        returnButton.addActionListener(new ReturnAction());

        buttonPanel.add(confirmButton);
        buttonPanel.add(returnButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public StationFrame(UserFrame caller)
    {
        this();
        this.caller = caller;
    }

    public StationFrame(UserFrame caller, int flag)
    {
        this(caller);
        this.flag = flag;
    }

    public JTable getStationTable()
    {
        return stationTable;
    }

    private class SelectAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int i = StationFrame.this.getStationTable().getSelectedRow();
            if (i == -1)
            {
                JOptionPane.showMessageDialog(StationFrame.this, "��ѡ��վ��Ϣ��");
                return;
            }
            String stationName = StationFrame.this.getStationTable().getValueAt(i, 0).toString();
            if (StationFrame.this.flag == 0)
                caller.getOriStation().setText(stationName);
            else
                caller.getDestinationStation().setText(stationName);
            dispose();
        }
    }

    private class ReturnAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dispose();
        }
    }
}
