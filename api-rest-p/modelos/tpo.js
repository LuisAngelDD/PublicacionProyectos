const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const modelos = mongoose.model;
//const bcrypt = require('bcryptjs')
const tipoProyectoSchema = Schema(
  {
    nombreTipoProyecto: {type: String,require: true}
  },
  {
    versionKey: false
  }
);
module.exports = modelos("tipoProyectos", tipoProyectoSchema);
