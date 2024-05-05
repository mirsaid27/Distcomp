package com.github.kornet_by.dc.lab3.service

import com.github.kornet_by.dc.lab3.dto.request.IssueRequestTo
import com.github.kornet_by.dc.lab3.dto.request.IssueRequestToId
import com.github.kornet_by.dc.lab3.dto.response.IssueResponseTo

interface IssueService {
	suspend fun create(requestTo: IssueRequestTo?): IssueResponseTo?

	suspend fun deleteById(id: Long): Boolean

	suspend fun getAll(): List<IssueResponseTo>

	suspend fun getById(id: Long): IssueResponseTo?

	suspend fun update(requestTo: IssueRequestToId?): IssueResponseTo?
}