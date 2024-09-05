package com.impact.brain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "space_request")
public class SpaceRequest {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private Request request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id")
    private Space space;

    @Column(name = "num_people")
    private Integer numPeople;

    @Column(name = "event_desc")
    private Integer eventDesc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private ResourceRequestStatus status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public Integer getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(Integer numPeople) {
        this.numPeople = numPeople;
    }

    public Integer getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(Integer eventDesc) {
        this.eventDesc = eventDesc;
    }

    public ResourceRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ResourceRequestStatus status) {
        this.status = status;
    }

}