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
    public static final String[] columnNames = {"����", "�г�����", "����ʱ��", "����ʱ��",
            "��Ӫʱ��", "��������", "ʼ��վ", "�յ�վ", "ƽ���ٶ�"};
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
        setLocationRelativeTo(null);                //������ʾ
        setResizable(false);

        setTitle("�г�����");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        field = new JTextField(12);
        queryButton = new JButton("����");
        queryButton.addActionListener(new QueryAction());
        northPanel = new JPanel();
        northPanel.add(field);
        northPanel.add(queryButton);

        trainTable = new JTable(TrainSQL.queryTrain(), columnNames);
        add(new JScrollPane(trainTable), BorderLayout.CENTER);
        trainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addButton = new JButton("�����г�");
        addButton.addActionListener(new AddAction());
        deleteButton = new JButton("ɾ���г�");
        deleteButton.addActionListener(new DeleteAction());
        modifyButton = new JButton("�鿴��ͣվ");
        modifyButton.addActionListener(new ModifyAction());
        returnButton = new JButton("������һ��");
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
                JOptionPane.showMessageDialog(AdmTrainFrame.this, "��ѡ��Ҫ�鿴���г���Ϣ��", "����", JOptionPane.WARNING_MESSAGE);
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
