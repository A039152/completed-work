package finance;

public class CertificateOfDeposit {
    private double faceValue;
    private double interestRate;
    private int term;
    private int compoundingPeriodsPerYear;


    // Constructor with faceValue, rate, and term
    public CertificateOfDeposit(double faceValue, double rate, int term) {
        this(faceValue, rate, term, 1);
    }

    // Constructor with faceValue, rate, term, and compounding periods
    public CertificateOfDeposit(double faceValue, double rate, int term, int compoundingPeriodsPerYear) {
        this.faceValue = faceValue;
        this.interestRate = rate;
        this.term = term;
        this.compoundingPeriodsPerYear = compoundingPeriodsPerYear;
    }

    public double calculateEndValue() {

        double r = interestRate / 100;

        // compound interest formula  -> A = P (1 + r/n)^(nt)
        double amount = faceValue * Math.pow(1 + r / compoundingPeriodsPerYear, compoundingPeriodsPerYear * term);

        return amount;
    }

    // Getters
    public double getFaceValue() {
        return faceValue;
    }

    public double getRate() {
        return interestRate;
    }

    public int getTerm() {
        return term;
    }

    public int getCompoundingPeriodsPerYear() {
        return compoundingPeriodsPerYear;
    }

    public String formatCurrency(double amount) {
        return String.format("$ %,10.2f", amount);
    }
}
