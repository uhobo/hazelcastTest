package com.entities;

public enum LockingProcessResult {
	FIRST_SUCCESS,
	CONT_SUCCESS,
	ALREADY_LOCKED,
	UNLOCKED_KEY_NOT_EXIST,
	UNLOCKED_DIFF_USER,
	UNLOCKED_SUCCESS;
}
