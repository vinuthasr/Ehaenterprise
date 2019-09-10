package com.elephant.dao.state;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elephant.domain.state.State;

public interface StateRepository extends JpaRepository<State, Long> {

}
