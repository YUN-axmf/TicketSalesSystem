package gui;

import dao.StationSQL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ����Ա����վ������
 */
public class AdmStaFrame extends JFrame
{
    public static void main(String[] args)
    {
        new AdmStaFrame();
    }

    public static final int DEFAULT_WIDTH = 350;
    public static final int DEFAULT_HEIGHT = 500;
    public static final String[] columnNames = {"��վ��", "����", "γ��"};
    private JTable stationTable;
    private JPanel northPanel;
    private JPanel southPanel;
    private JTextField field;
    private JButton queryButton;
    private JButton addButton;
    private JButton returnButton;

    public AdmStaFrame()
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //������ʾ
        setResizable(false);

        setTitle("��վ����");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        field = new JTextField(8);
        queryButton = new JButton("����");
        queryButton.addActionListener(new QueryAction());
        northPanel = new JPanel();
        northPanel.add(field);
        northPanel.add(queryButton);

        stationTable = new JTable(StationSQL.queryStation(), columnNames);
        add(new JScrollPane(stationTable), BorderLayout.CENTER);
        stationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addButton = new JButton("���ӳ�վ");
        addButton.addActionListener(new AddAction());
        returnButton = new JButton("������һ��");
        returnButton.addActionListener(new ReturnAction());

        southPanel = new JPanel();
        southPanel.add(addButton);
        southPanel.add(returnButton);

        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class QueryAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String staName = field.getText().trim();
            if (staName.equals(""))
            {
                JOptionPane.showMessageDialog(AdmStaFrame.this, "������Ҫ���ҵĳ�վ��Ϣ��", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!StationSQL.isStaExisting(staName))
            {
                JOptionPane.showMessageDialog(AdmStaFrame.this, "�ó�վ�����ڣ�", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            new StaInfoFrame(staName, 0);
            dispose();
        }
    }

    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new StaInfoFrame("", 1);
            dispose();
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
