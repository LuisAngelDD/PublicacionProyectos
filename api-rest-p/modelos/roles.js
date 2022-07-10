const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const modelos = mongoose.model;
//const bcrypt = require('bcryptjs')
const rolesSchema = Schema(
  {
    nombreRol: {type: String,require: true}
  },
  {
    versionKey: false
  }
);
module.exports = modelos("roles", rolesSchema);
