package compta.persistence.entity.enums;

public enum INVOICE_STATE
{
    QUOTE(1, "devis"), INVOICE(2, "facture"), INVOICE_CANCEL(3, "facture annulée");

    private Integer id;

    private String description;

    private INVOICE_STATE(int id, String description)
    {
        this.id = id;
        this.description = description;
    }

    public Integer getKey(INVOICE_STATE ct)
    {
        return ct.id;
    }

    public INVOICE_STATE valueOf(Integer id)
    {
        for (final INVOICE_STATE type : values ())
        {
            if (type.id.equals (id))
            {
                return type;
            }
        }
        return null;
    }

    public String getDescription()
    {
        return this.description;
    }
}
