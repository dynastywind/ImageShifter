package cisc.awas.image_shifter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "image_rows")
public class ImageRows {

	@Id
	@Column(name = "rows")
	private long rows;

	public long getRows() {
		return rows;
	}

	public void setRows(long rows) {
		this.rows = rows;
	}
	
}
