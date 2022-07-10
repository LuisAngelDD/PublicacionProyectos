const {Schema,model} = require('mongoose');
const likesSchema = Schema(
  {
    codeProyecto:{type:String,require: true,unique: true},
    likes:[{type: String,unique: true}],
    count:{type:Number,default: 0 }
  },
  {
    timestamps: true,
    versionKey: false
  }
);
module.exports = model("likes", likesSchema);
