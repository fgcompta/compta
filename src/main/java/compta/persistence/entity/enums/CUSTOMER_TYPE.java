package compta.persistence.entity.enums;

public enum CUSTOMER_TYPE
{
    CLIENT(1, "client"), COLLECTIVITE(2, "collectivité"), SOCIETE(3, "societé");

    private Integer id;

    private String description;

    private CUSTOMER_TYPE(int id, String description)
    {
        this.id = id;
        this.description = description;
    }

    public Integer getKey(CUSTOMER_TYPE ct)
    {
        return ct.id;
    }

    public CUSTOMER_TYPE valueOf(Integer id)
    {
        for (final CUSTOMER_TYPE type : values ())
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
