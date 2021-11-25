<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model backend\models\Contributors */
/* @var $modelUsers backend\models\Users */
/* @var $modelCompanies backend\models\Companies */

$this->title = 'Update Contributor: ' .$model->user->username.' ('.$model->company->companyname.')';
?>
<div class="contributors-update">
    <div class="contributors-content">
        <h1><?= Html::encode($this->title) ?></h1>
        <p>
            <?= Html::a('Back', ['index'], ['class' => 'btn btn-light']) ?>
        </p>
        <?= $this->render('_form', [
            'model' => $model, 'modelUsers' => $modelUsers, 'modelCompanies' => $modelCompanies
        ]) ?>
    </div>
</div>
