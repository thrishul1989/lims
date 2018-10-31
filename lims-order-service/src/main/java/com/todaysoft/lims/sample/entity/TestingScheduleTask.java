package com.todaysoft.lims.sample.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.todaysoft.lims.persist.AutoKeyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "LIMS_TESTING_SCHEDULE_TASK")
public class TestingScheduleTask extends AutoKeyEntity {/**
	 * 
	 */
	private static final long serialVersionUID = -8439276290840413067L;
	
//	private TestingSchedule testingSchedule;
	
	private Integer scheduleId;
	
	private Integer taskNodeId;
	
	

	@Column(name = "testing_schedule_id")
	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	@Column(name = "task_node_id")
	public Integer getTaskNodeId() {
		return taskNodeId;
	}

	public void setTaskNodeId(Integer taskNodeId) {
		this.taskNodeId = taskNodeId;
	}

/*	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "schedule_id")
	@JsonIgnore
	public TestingSchedule getTestingSchedule() {
		return testingSchedule;
	}

	public void setTestingSchedule(TestingSchedule testingSchedule) {
		this.testingSchedule = testingSchedule;
	}*/
	
	
}
