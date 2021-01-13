package com.imuges.order.excel


/**
 * @author BGQ
 * excel对应实体
 */

/**
 * 单元格，对应列
 */
data class Cell(var text: String)

/**
 * 行
 */
data class Row(var cells: MutableList<Cell>)

/**
 * 表
 */
data class Sheet(var name: String, var rows: MutableList<Row>)

/**
 * Excel
 */
data class Excel(var name: String, var sheets: MutableList<Sheet>)