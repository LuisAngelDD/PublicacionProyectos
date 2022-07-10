const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const modelos = mongoose.model;
const usuarioSchema = Schema(
  {
    correo: {type: String,require: true,unique: true},
    password: {type: String,required: true},
    auth: {type: Boolean,required: true},
  },
  {
    timestamps: true,
    versionKey: false
  }
);
module.exports = modelos("usuarios", usuarioSchema);
