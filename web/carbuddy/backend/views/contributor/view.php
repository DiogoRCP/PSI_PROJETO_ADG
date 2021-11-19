<?php

use yii\helpers\Html;
use yii\widgets\DetailView;

/* @var $this yii\web\View */
/* @var $model backend\models\Contributors */

$this->title = $model->user->username.' ('.$model->company->companyname.')';
?>
<div class="contributors-view">
    <img src="../images/logo_white.png" width="80">
    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Back', ['index'], ['class' => 'btn btn-light']) ?>
        <?= Html::a('Update', ['update', 'id' => $model->id], ['class' => 'btn btn-secondary']) ?>
        <?= Html::a('Delete', ['delete', 'id' => $model->id], [
            'class' => 'btn btn-dark',
            'data' => [
                'confirm' => 'Are you sure you want to delete this item?',
                'method' => 'post',
            ],
        ]) ?>
    </p>

    <?= DetailView::widget([
        'model' => $model,
        'attributes' => [
            //'id',

            [
                'label' => 'User',
                'value' => $model->user->username,
            ],
            [
                'label' => 'Company',
                'value' => $model->company->companyname
            ],
            'speciality',
        ],
    ]) ?>

</div>
