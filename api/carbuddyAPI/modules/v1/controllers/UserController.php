<?php

namespace app\modules\v1\controllers;
use yii\rest\ActiveController;
use yii\filters\auth\HttpBasicAuth;
class UserController extends ActiveController
{
    public $modelClass = 'app\models\User';
}
