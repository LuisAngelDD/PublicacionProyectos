const {Schema,model} = require('mongoose');
const proyectosSchema = Schema(
  {
    nombreProyecto: {type: String,require: true},
    descripcionProyecto: {type: String,require: true},
    tipoProyecto: {type: String,require: true},
    authorEmailProyecto:{type:String,require: true},
    authorNameProyecto:{type:String,require: true},
    statusProyecto:{type:String,require: true},
    codeProyecto:{type:String,require: true,unique: true}
  },
  {
    timestamps: true,
    versionKey: false
  }
);
module.exports = model("proyectos", proyectosSchema);
