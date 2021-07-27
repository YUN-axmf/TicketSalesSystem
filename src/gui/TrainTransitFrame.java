package gui;

import dao.TrainSQL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrainTransitFrame extends JFrame
{
    public static void main(String[] args)
    {
        new TrainTransitFrame("G7262");
    }
    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 600;
    private String userID = null;
    public static final String[] columnNames = {"վ��", "վ��", "����ʱ��", "ͣ��ʱ��", "����ʱ��", "���"};
    private JPanel northPanel;
    private JPanel southPanel;
    private JLabel trainLabel;
    private JTextField triNoField;
    private JTable transitTable;
    private String triNo;
    private JButton addButton;
    private JButton deleteButton;
    private JButton modifyButton;
    private JButton returnButton;

    public TrainTransitFrame(String triNo)
    {
        this.triNo = triNo;

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //������ʾ
        setResizable(false);

        setTitle("ʱ�̱�");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.userID = userID;

        northPanel = new JPanel();
        trainLabel = new JLabel("���κ�");

        triNoField = new JTextField(triNo);
        triNoField.setEditable(false);

        northPanel.add(trainLabel);
        northPanel.add(triNoField);

        transitTable = new JTable(TrainSQL.queryTransit(triNo), columnNames);
        add(new JScrollPane(transitTable), BorderLayout.CENTER);
        transitTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addButton = new JButton("����");
        deleteButton = new JButton("ɾ��");
        modifyButton = new JButton("�޸�");
        returnButton = new JButton("����");
        returnButton.addActionListener(new ReturnAction());

        southPanel = new JPanel();
        //southPanel.add(addButton);
        //southPanel.add(deleteButton);
        //southPanel.add(modifyButton);
        southPanel.add(returnButton);

        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class ReturnAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dispose();
            new AdmTrainFrame();
        }
    }
}
