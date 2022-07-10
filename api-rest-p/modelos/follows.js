const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const modelos = mongoose.model;
const followsSchema = Schema(
  {
    codeProyecto:{type:String,require: true,unique: true},
    follows:[{type: String}],
    count:{type:Number,default: 0 },
  },
  {
    timestamps: true,
    versionKey: false
  }
);
module.exports = modelos("follows", followsSchema);
