package finance;

public class TestProgram {

    public static void main(String[] args) {

        int faceValue = 1000;
        double rate = 0.05;
        int term = 10;
        int numberOfPeriods;
        CertificateOfDeposit cd1 = new CertificateOfDeposit(faceValue,rate,term);
        System.out.println(cd1.formatCurrency(cd1.getFaceValue()) + " at " + cd1.getRate() / 100 + "% for " + cd1.getTerm() + " years -> " + cd1.formatCurrency(cd1.calculateEndValue()));

        faceValue = 800;
        rate = 0.045;
        term = 8;
        CertificateOfDeposit cd2 = new CertificateOfDeposit(faceValue,rate,term);
        System.out.println(cd2.formatCurrency(cd2.getFaceValue()) + " at " + cd2.getRate() / 100 + "% for " + cd2.getTerm() + " years -> " + cd2.formatCurrency(cd2.calculateEndValue()));


        faceValue = 1000;
        rate = 0.05;
        term = 10;
        numberOfPeriods = 2;
        CertificateOfDeposit cd3 = new CertificateOfDeposit(faceValue,rate,term, numberOfPeriods);
        System.out.println(cd3.formatCurrency(cd3.getFaceValue()) + " at " + cd3.getRate() / 100 + "% compounding " + cd3.getCompoundingPeriodsPerYear() + " time(s) per year for " + cd3.getTerm() + " years -> " + cd3.formatCurrency(cd3.calculateEndValue()));

        //TODO create a CD and get its value at maturity

        faceValue = 800;
        rate = 0.045;
        term = 8;
        numberOfPeriods = 12;
        CertificateOfDeposit cd4 = new CertificateOfDeposit(faceValue,rate,term, numberOfPeriods);
        System.out.println(cd4.formatCurrency(cd4.getFaceValue()) + " at " + cd4.getRate() / 100 + "% compounding " + cd4.getCompoundingPeriodsPerYear() + " time(s) per year for " + cd4.getTerm() + " years -> " + cd4.formatCurrency(cd4.calculateEndValue()));
    }
}
