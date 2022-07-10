const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const modelos = mongoose.model;
const likesSchema = Schema(
  {
    codeProyecto:{type:String,require: true,unique: true},
    likes:[{type: String}],
    count:{type:Number,default: 0 },
  },
  {
    timestamps: true,
    versionKey: false
  }
);
module.exports = modelos("likes", likesSchema);
