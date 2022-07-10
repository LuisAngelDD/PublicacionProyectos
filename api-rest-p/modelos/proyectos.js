const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const modelos = mongoose.model;
const proyectosSchema = Schema(
      {
        nombreProyecto: {type: String,require: true},
        descripcionProyecto: {type: String,require: true},
        tipoProyecto: {type: String,require: true},
        authorEmailProyecto:{type:String,require: true},
        authorNameProyecto:{type:String,require: true},
        statusProyecto:{type:String,require: true},
      },
      {
        timestamps: true,
        versionKey: false
      }
);
module.exports = modelos("proyectos", proyectosSchema);
