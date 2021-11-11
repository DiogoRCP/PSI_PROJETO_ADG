<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model frontend\models\Cars */

$this->title = 'Update Vehicle: ' . $model->user->username . ' (' . $model->brand . ' ' . $model->model . ')';

?>
<div class="cars-update">
    <div class="cars-content">
        <img src="../images/logo_white.png" width="80">
        <h1><?= Html::encode($this->title) ?></h1>
        <p>
            <?= Html::a('Back', ['index'], ['class' => 'btn btn-dark']) ?>
        </p>
        <?= $this->render('_form', [
            'model' => $model,
        ]) ?>
    </div>
</div>
