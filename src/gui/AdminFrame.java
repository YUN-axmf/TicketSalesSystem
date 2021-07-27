package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ����Ա����������
 */
public class AdminFrame extends JFrame
{
    public static void main(String[] args)
    {
        new AdminFrame();
    }

    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 400;
    private JLabel text;
    private JButton stationButton;
    private JButton trainButton;
    private JButton ticketButton;
    private JButton userButton;

    public AdminFrame()
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //������ʾ
        setLayout(null);
        setResizable(false);

        text = new JLabel("��ѡ����в����Ķ���:");
        text.setBounds(50, 70, 200, 30);

        stationButton = new JButton("��վ");
        stationButton.setBounds(70, 140, 100, 50);
        stationButton.addActionListener(new StationAction());
        trainButton = new JButton("�г�");
        trainButton.setBounds(220, 140, 100, 50);
        trainButton.addActionListener(new TrainAction());
        ticketButton = new JButton("��Ʊ");
        ticketButton.setBounds(70, 230, 100, 50);
        ticketButton.addActionListener(new TicketAction());
        userButton = new JButton("�û�");
        userButton.setBounds(220, 230, 100, 50);
        userButton.addActionListener(new UserAction());

        add(text);
        add(stationButton);
        add(trainButton);
        add(ticketButton);
        add(userButton);

        setTitle("����Աҳ��");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private class StationAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new AdmStaFrame();
            dispose();
        }
    }

    private class TrainAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new AdmTrainFrame();
            dispose();
        }
    }

    private class TicketAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new AdmTicketFrame();
            dispose();
        }
    }

    private class UserAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dispose();
            new AdmUserFrame();
        }
    }
}
