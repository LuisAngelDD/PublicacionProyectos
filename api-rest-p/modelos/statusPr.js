const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const modelos = mongoose.model;
const estadoSchema = Schema(
  {
    statusProyect: {type: String,require: true},
  },
  {
    versionKey: false
  }
);
module.exports = modelos("statusprs", estadoSchema);
