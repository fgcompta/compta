package compta.persistence.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import lombok.Data;

import compta.persistence.util.EntityConstant;
import compta.persistence.util.IEntity;


@Entity
@Table(name = "article")
@Data
public class Article implements IEntity<Integer>
{

    private static final long serialVersionUID = 5766021907013418911L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer primaryKey;

    @Column(name = "article_name", length = EntityConstant.MAX_SIZE_VARCHAR)
    private String name;

    @Column(name = "reference", length = EntityConstant.AVG_SIZE_VARCHAR)
    private String reference;

    @Column(name = "price_ht")
    @Digits(integer = EntityConstant.DECIMAL_ALL_PARTS, fraction = EntityConstant.DECIMAL_PART)
    private BigDecimal priceHT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "vat_rate_id")
    private VatRate rate;

    @Column(name = "display")
    private Boolean display;

    @Column(name = "decimal_quantity")
    private Boolean decimalQuantity;
}

/**
 *
 *
 *
 * CREATE TABLE invoice_line(
 * id INT(11) NOT NULL AUTO_INCREMENT,
 * article_id INT(11) NOT NULL,
 * invoice_id INT(11) NOT NULL,
 * quantity DECIMAL (7,2) NOT NULL,
 * price_ht DECIMAL (7,2) NOT NULL,
 * discount DECIMAL (4,2) NULL,
 * vat_rate DECIMAL (4,2) NOT NULL,
 * PRIMARY KEY (id),
 * CONSTRAINT `fk_invoice_line_article` FOREIGN KEY (`article_id`) REFERENCES
 * `test`.`article` (`id`),
 * CONSTRAINT `fk_invoice_line_invoice` FOREIGN KEY (`invoice_id`) REFERENCES
 * `test`.`invoice` (`id`)
 * )ENGINE=`InnoDB` AUTO_INCREMENT=1 DEFAULT CHARACTER SET utf8 COLLATE
 * utf8_unicode_ci ROW_FORMAT=COMPACT CHECKSUM=0 DELAY_KEY_WRITE=0;
 *
 * CREATE TABLE invoice_payment_mean(
 * id INT(11) NOT NULL AUTO_INCREMENT,
 * payment_mean_id INT(11) NOT NULL,
 * invoice_id INT(11) NOT NULL,
 * amount DECIMAL (7,2) NOT NULL,
 * PRIMARY KEY (id),
 * CONSTRAINT `fk_invoice_payment_mean_invoice` FOREIGN KEY (`invoice_id`)
 * REFERENCES `test`.`invoice` (`id`),
 * CONSTRAINT `fk_invoice_payment_mean_payment_mean` FOREIGN KEY
 * (`payment_mean_id`) REFERENCES `test`.`payment_mean` (`id`)
 * )ENGINE=`InnoDB` AUTO_INCREMENT=1 DEFAULT CHARACTER SET utf8 COLLATE
 * utf8_unicode_ci ROW_FORMAT=COMPACT CHECKSUM=0 DELAY_KEY_WRITE=0;
 *
 * CREATE TABLE article_stock(
 * id INT(11) NOT NULL AUTO_INCREMENT,
 * article_id INT(11) NOT NULL,
 * quantity_init INT,
 * quantity INT NOT NULL,
 * date_entry DATETIME,
 * invoice_supplier VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci,
 * unit_price DECIMAL (7,2) NOT NULL,
 * PRIMARY KEY (id),
 * CONSTRAINT `fk_article_stock_articles` FOREIGN KEY (`article_id`) REFERENCES
 * `test`.`article` (`id`)
 * )ENGINE=`InnoDB` AUTO_INCREMENT=1 DEFAULT CHARACTER SET utf8 COLLATE
 * utf8_unicode_ci ROW_FORMAT=COMPACT CHECKSUM=0 DELAY_KEY_WRITE=0;
 */