const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const modelos = mongoose.model;
const usuarioDataSchema = Schema(
  {
    nombre: {type: String,require: true},
    correo:{type: String,require: true,unique: true},
    public_id:{type:String},
    secure_url:{type:String},
    ocupacion:{type: String,require: true},
    about:{type: String},
  },
  {
    timestamps: true,
    versionKey: false
  }
);
module.exports = modelos("usuariosdata", usuarioDataSchema);