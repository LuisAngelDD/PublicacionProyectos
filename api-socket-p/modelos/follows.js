const {Schema,model} = require('mongoose');
const followsSchema = Schema(
  {
    codeProyecto:{type:String,require: true,unique: true},
    follows:[{type: String,unique: true}],
    count:{type:Number,default: 0 }
  },
  {
    timestamps: true,
    versionKey: false
  }
);
module.exports = model("follows", followsSchema);
